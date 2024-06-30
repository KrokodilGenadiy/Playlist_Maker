package com.zaus_app.playlistmaker.presentation.fragments.playlist_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.interactors.FavoritesInteractor
import com.zaus_app.playlistmaker.domain.interactors.PlaylistInteractor
import com.zaus_app.playlistmaker.domain.interactors.SettingsInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistDetailsViewModel(
private val playlistsInteractor: PlaylistInteractor,
private val favoritesInteractor: FavoritesInteractor,
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private val statePlaylistLiveData = MutableLiveData<PlaylistDetailsState>()
    fun observeState(): LiveData<PlaylistDetailsState> = statePlaylistLiveData

    private var listTrack: List<Track> = mutableListOf()

    var playlist: Flow<Playlist> = emptyFlow()

    fun getData() {
        viewModelScope.launch {
            playlist.collectLatest { playlist ->
                val list: MutableList<Track> = mutableListOf()
                playlist.tracks.forEach {
                    val track = favoritesInteractor.getPlaylistTrackById(it)
                    if (track != null)
                        list.add(track)
                }
                list.reverse()
                listTrack = list
                preparingPlaylistData()
            }
        }
    }

    fun updatePlaylist() {
        viewModelScope.launch {
        playlist.collectLatest {
                playlist = flowOf(playlistsInteractor.getPlaylistById(it.id))
            }
        }
        getData()
    }

    private fun preparingPlaylistData() {
        viewModelScope.launch {
            playlist.collectLatest {
                statePlaylistLiveData.postValue(
                    PlaylistDetailsState.Content(
                        it.urlImage,
                        it.playlistName,
                        it.description,
                        getDurationAllTrack(listTrack),
                        listTrack.size.toString() + " " +
                                changeRussianWordsAsTracks(listTrack.size),
                        listTrack
                    )
                )
            }
        }
    }

    fun deletePlaylist() {
        viewModelScope.launch {
            playlist.collectLatest {
                playlistsInteractor.deletePlaylist(it.id)
                statePlaylistLiveData.postValue(PlaylistDetailsState.Delete)
            }


        }
    }

    private fun getDurationAllTrack(tracksInPlaylist: List<Track>?): String {
        var duration = 0
        viewModelScope.launch {
            tracksInPlaylist?.map {
                duration += it.trackTimeMillis?.toInt() ?: 0
            }
        }
        return msToMm(duration.toString()) + " " + changeRussianWordsAsMinutes(duration)
    }

    fun deleteTrackFromPlaylist(track: Track) {
        viewModelScope.launch {
            playlist.collectLatest {
                val playlist = playlistsInteractor.getPlaylistById(it.id)
                playlistsInteractor.updatePlaylistAndDeleteTrack(
                    track,
                    playlist
                )
                if (listTrack.size <= 1) {
                    statePlaylistLiveData.postValue(PlaylistDetailsState.Delete)
                } else {
                    preparingPlaylistData()
                }
            }

        }
    }

    fun sharePlaylist() {
        viewModelScope.launch {
           playlist.collectLatest {
               val list: StringBuilder = StringBuilder()
               listTrack.mapIndexed { index: Int, track: Track ->
                   list.append("${index + 1}. ${track.artistName} - ${track.trackName} (${msToSs(track.trackTimeMillis)})\n")
               }
               val messages = "${it.playlistName}\n" +
                       "${it.description}\n" +
                       "[${it.tracksCount}] ${changeRussianWordsAsTracks(it?.tracksCount ?: 0)}\n" +
                       list
               settingsInteractor.sharePlaylist(messages)
           }
        }
    }

    fun changeRussianWordsAsMinutes(countMinuteMillis: Int): String {
        Log.d("=== LOG ===", "=== changeRussianWordsAsMinutes ${countMinuteMillis}")
        val countMinute = countMinuteMillis / 60000
        val lastDigit = countMinute % 10
        val lastTwoDigits = countMinute % 100

        return when {
            lastTwoDigits in 11..19 -> "минут"
            lastDigit == 1 -> "минута"
            lastDigit in 2..4 -> "минуты"
            else -> "минут"
        }
    }

    internal fun changeRussianWordsAsTracks(countTrack: Int): String {
        val num = countTrack % 100
        return when {
            num in 10..20 -> "треков"
            num % 10 == 1 -> "трек"
            num % 10 in 2..4 -> "трека"
            else -> "треков"
        }
    }

    fun msToMm(timeInMilliseconds: String): String {
        return SimpleDateFormat("mm", Locale.getDefault()).format(timeInMilliseconds.toInt())
    }

    fun msToSs(ms: Long?): String = SimpleDateFormat("mm:ss", Locale.getDefault()).format(ms)
}
package com.beomjo.whitenoise.viewmodel

import android.net.Uri
import androidx.lifecycle.Observer
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.BaseTest.DynamicCall.Companion.withArguments
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.repositories.player.PlayerRepository
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.ui.player.PlayerServiceConnection
import com.beomjo.whitenoise.utilities.ext.isPlay
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Test

class PlayerManagerTest : BaseTest() {

    @MockK
    lateinit var playerRepository: PlayerRepository

    @MockK
    lateinit var playerServiceConnection: PlayerServiceConnection


    private lateinit var playerManager: PlayerManager

    override fun onBefore() {
        every { playerServiceConnection.playbackState.value!!.isPlay } returns true
        every { playerServiceConnection.isLoop.value } returns false

        playerManager = spyk(
            PlayerManager(playerRepository, playerServiceConnection),
            recordPrivateCalls = true,
        )
    }

    @Test
    fun `init() 호출`() {
        //given
        justRun { playerServiceConnection.subscribe() }

        //when
        playerManager.init()

        //then
        verify { playerServiceConnection.subscribe() }
    }

    @Test
    fun `track 설정`() {
        //given
        val track = mockk<Track>()
        val trackObserver = mockk<Observer<Track?>> {
            every { onChanged(track) } just Runs
        }
        playerManager.track.observeForever(trackObserver)
        justRun { playerManager invoke "loadTrack" withArguments listOf(track) }

        //when
        playerManager.setTrack(track)

        //then
        verify { trackObserver.onChanged(eq(track)) }
        verify { playerManager invoke "loadTrack" withArguments listOf(track) }
    }

    @Test
    fun `track 로드`() {
        //given
        val track = mockk<Track>()
        val storagePath = "/storage/111"
        val downloadUri = mockk<Uri>()
        every { track.storagePath } returns storagePath
        coEvery { playerRepository.getTrackDownloadUrl(storagePath) } returns downloadUri
        justRun { playerServiceConnection.prepareAndPlay(any(), any()) }

        //when
        playerManager invoke "loadTrack" withArguments listOf(track)

        //then
        coVerify { playerRepository.getTrackDownloadUrl(eq(storagePath)) }
        verify { playerServiceConnection.prepareAndPlay(eq(downloadUri), eq(track)) }
    }

    @Test
    fun `track 로드 실패`() {
        //given
        val track = mockk<Track>()
        val storagePath = "/storage/111"
        val downloadUri = mockk<Uri>()
        every { track.storagePath } returns storagePath
        coEvery { playerRepository.getTrackDownloadUrl(storagePath) } returns downloadUri
        justRun { playerServiceConnection.prepareAndPlay(any(), any()) }

        //when
        playerManager invoke "loadTrack" withArguments listOf(track)

        //then
        coVerify { playerRepository.getTrackDownloadUrl(eq(storagePath)) }
        verify { playerServiceConnection.prepareAndPlay(eq(downloadUri), eq(track)) }
    }

    @Test
    fun `Play 상태일때 버튼 클릭`() {
        //given
        every { playerManager.isPlaying.value } returns true

        //when
        playerManager.onPlayOrPause()

        //then
        verify { playerServiceConnection.pause() }
    }

    @Test
    fun `Pause 상태일때 버튼 클릭`() {
        //given
        every { playerManager.isPlaying.value } returns false

        //when
        playerManager.onPlayOrPause()

        //then
        verify { playerServiceConnection.play() }
    }

    @Test
    fun `Track Data가 존재할 때 바텀 Player를 눌러 Expand`() {
        //given
        val track = mockk<Track>()
        every { playerManager.hasData.value } returns true
        every { playerManager.track.value } returns track
        val moveToPlayerActivityObserver = mockk<Observer<Track>> {
            every { onChanged(track) } just Runs
        }
        playerManager.moveToPlayerActivity.observeForever(moveToPlayerActivityObserver)

        //when
        playerManager.onExpand()

        //then
        verify { moveToPlayerActivityObserver.onChanged(eq(track)) }
    }

    @Test
    fun `Loop 버튼 클릭`() {
        //given
        every { playerManager.isLoop.value } returns false

        //when
        playerManager.onPerformLoop()

        //then
        verify { playerServiceConnection.setLoop(true) }
    }

    @Test
    fun `onCleared 호출`() {
        //given

        //when
        playerManager.onCleared()

        //then
        verify { playerServiceConnection.unsubscribe() }
    }
}
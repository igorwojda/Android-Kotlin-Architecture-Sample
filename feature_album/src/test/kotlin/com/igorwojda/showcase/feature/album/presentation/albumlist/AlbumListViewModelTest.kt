package com.igorwojda.showcase.feature.album.presentation.albumlist

import com.igorwojda.showcase.base.domain.result.Result
import com.igorwojda.showcase.base.presentation.nav.NavManager
import com.igorwojda.showcase.feature.album.domain.model.Album
import com.igorwojda.showcase.feature.album.domain.usecase.GetAlbumListUseCase
import com.igorwojda.showcase.library.testutils.CoroutinesTestDispatcherExtension
import com.igorwojda.showcase.library.testutils.InstantTaskExecutorExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantTaskExecutorExtension::class, CoroutinesTestDispatcherExtension::class)
class AlbumListViewModelTest {

    private val mockGetAlbumListUseCase: GetAlbumListUseCase = mockk()

    private val mockNavManager: NavManager = mockk(relaxed = true)

    private val cut = AlbumListViewModel(
        mockNavManager,
        mockGetAlbumListUseCase
    )

    @Test
    fun `onEnter album list is empty`() = runTest {
        // given
        coEvery { mockGetAlbumListUseCase.execute() } returns Result.Success(emptyList())

        // when
        cut.onEnter()

        // then
        advanceUntilIdle()

        cut.stateFlow.value shouldBeEqualTo AlbumListViewModel.State(
            isLoading = false,
            isError = true,
            albums = listOf()
        )
    }

    @Test
    fun `onEnter album list is non-empty`() = runTest {
        // given
        val album = Album("albumName", "artistName")
        val albums = listOf(album)
        coEvery { mockGetAlbumListUseCase.execute() } returns Result.Success(albums)

        // when
        cut.onEnter()

        // then
        advanceUntilIdle()

        cut.stateFlow.value shouldBeEqualTo AlbumListViewModel.State(
            isLoading = false,
            isError = false,
            albums = albums
        )
    }

    @Test
    fun `onAlbumClick navigate to album detail`() {
        // given
        val artistName = "Michael Jackson"
        val albumName = "Thriller"
        val mbId = "mbId"

        val album = Album(
            artist = artistName,
            name = albumName,
            mbId = mbId
        )

        val navDirections = AlbumListFragmentDirections.actionAlbumListToAlbumDetail(
            artistName,
            albumName,
            mbId
        )

        // when
        cut.onAlbumClick(album)

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }
}

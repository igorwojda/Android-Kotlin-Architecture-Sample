package com.igorwojda.showcase.feature.album.domain.usecase

import com.igorwojda.showcase.feature.album.data.AlbumRepositoryImpl
import com.igorwojda.showcase.feature.album.domain.model.Album
import com.igorwojda.showcase.feature.album.domain.usecase.GetAlbumUseCase.Result.Error
import com.igorwojda.showcase.feature.album.domain.usecase.GetAlbumUseCase.Result.Success
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.net.UnknownHostException

class GetAlbumUseCaseTest {

    private val mockAlbumRepository: AlbumRepositoryImpl = mockk()

    private val cut = GetAlbumUseCase(mockAlbumRepository)

    @Test
    fun `when execute then return album`() {
        // given
        val albumName = "Thriller"
        val artistName = "Michael Jackson"
        val mbId = "123"

        val album = mockk<Album>()
        coEvery { mockAlbumRepository.getAlbumInfo(artistName, albumName, mbId) } answers { album }

        // when
        val actual = runBlocking { cut.execute(artistName, albumName, mbId) }

        // then
        actual shouldBeEqualTo Success(album)
    }

    @Test
    fun `when execute then return error`() {
        // given
        val albumName = "Thriller"
        val artistName = "Michael Jackson"
        val mbId = "123"
        val exception = UnknownHostException()

        coEvery { mockAlbumRepository.getAlbumInfo(artistName, albumName, mbId) } throws exception

        // when
        val actual = runBlocking { cut.execute(artistName, albumName, mbId) }

        // then
        coVerify { mockAlbumRepository.getAlbumInfo(artistName, albumName, mbId) }
        actual shouldBeEqualTo Error(exception)
    }
}
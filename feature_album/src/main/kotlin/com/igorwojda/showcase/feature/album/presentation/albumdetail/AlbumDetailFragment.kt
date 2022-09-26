package com.igorwojda.showcase.feature.album.presentation.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.igorwojda.showcase.feature.album.R
import com.igorwojda.showcase.feature.album.presentation.albumdetail.AlbumDetailViewModel.State
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class AlbumDetailFragment : Fragment() {
    private val args: AlbumDetailFragmentArgs by navArgs()
    private val model: AlbumDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model.onEnter(args)

        return ComposeView(requireContext()).apply {
            setContent {
                AlbumDetailScreen(uiStateFlow = model.uiStateFlow)
            }
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun AlbumDetailScreen(uiStateFlow: StateFlow<State>) {
    // Collecting flows in a lifecycle-aware manner
    // collectAsStateWithLifecycle does not waste resources when collecting flows from the UI layer
    // More: https://medium.com/androiddevelopers/consuming-flows-safely-in-jetpack-compose-cde014d0d5a3
    val state: State by uiStateFlow.collectAsStateWithLifecycle()

    Column {
        Text(text = state.albumName)
        Text(text = state.artistName)
        AsyncImage(
            model = state.coverImageUrl,
            contentDescription = stringResource(id = R.string.album_cover_content_description)
        )
    }
}

package com.igorwojda.showcase.base.presentation.compose

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LabeledAnimation(@StringRes label: Int, @RawRes assetResId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ) {
        Text(text = stringResource(label), fontSize = 18.sp)
        LottieAssetLoader(assetResId)
    }
}

@Composable
fun LottieAssetLoader(@RawRes assetResId: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(assetResId))
    LottieAnimation(
        composition,
        modifier = Modifier.requiredSize(150.dp)
    )
}

package com.example.neogulmap.presentation.ui.components

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapView

import com.kakao.vectormap.MapLifeCycleCallback

@Composable
fun KakaoMap(
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val mapView = MapView(context)
            mapView.start(
                object : MapLifeCycleCallback() {
                    override fun onMapDestroy() {
                        // Handle map destroy
                    }
                    override fun onMapError(error: Exception?) {
                        // Handle map error
                    }
                },
                object : KakaoMapReadyCallback() {
                    override fun onMapReady(kakaoMap: KakaoMap) {
                        // Map is ready
                    }
                }
            )
            mapView
        },
        update = { mapView ->
            // Update logic
        }
    )
}

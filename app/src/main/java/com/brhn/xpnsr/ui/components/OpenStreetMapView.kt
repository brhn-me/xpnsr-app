package com.brhn.xpnsr.ui.components


import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


data class CityInfo(val name: String, val latitude: Double, val longitude: Double, val data: Int)

val finlandCities = listOf(
    CityInfo("Oulu", 65.0121, 25.4651, 130),
    CityInfo("Vantaa", 60.2934, 25.0375, 15),
    CityInfo("Tampere", 61.4981, 23.7608, 32),
    CityInfo("Helsinki", 60.1695, 24.9354, 50)
)


@Composable
fun OpenStreetMapView(modifier: Modifier = Modifier.fillMaxSize()) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            initializeOsmdroid(ctx)
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)

                finlandCities.forEach { city ->
                    val marker = Marker(this)
                    marker.position = GeoPoint(city.latitude, city.longitude)
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    marker.title = "${city.name}: ${city.data}"
                    overlays.add(marker)
                }

                // Adjust the bounding box calculation for padding
                val boundingBox = BoundingBox.fromGeoPoints(finlandCities.map {
                    GeoPoint(it.latitude, it.longitude)
                }).increaseByScale(1.1f) // Add 10% padding around the bounding box

                // Use post to ensure the mapView is ready before zooming
                post {
                    zoomToBoundingBox(boundingBox, true)
                }
            }
        }
    )
}

private fun initializeOsmdroid(context: Context) {
    Configuration.getInstance()
        .load(context, android.preference.PreferenceManager.getDefaultSharedPreferences(context))
}
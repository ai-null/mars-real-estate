package com.gabutproject.mars_rent.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabutproject.mars_rent.network.MarsApi
import com.gabutproject.mars_rent.network.MarsApiFilter
import com.gabutproject.mars_rent.network.MarsProperty
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.random.Random

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus> get() = _status

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>> get() = _properties

    private val _selectedItemData = MutableLiveData<MarsProperty?>()
    val selectedItemData: LiveData<MarsProperty?> get() = _selectedItemData

    init {
        // pass show all as a default value
        // we want to show all items in the first time
        getMarsRealEstateData(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateData(type: MarsApiFilter) {
        uiScope.launch {
            try {
                // set status to loading and update to done after fetching is completed
                _status.value = MarsApiStatus.LOADING
                val listResult = MarsApi.retrofitService.getPropertiesAsync(type.value)
                _status.value = MarsApiStatus.DONE

                // set the value
                _properties.value = listResult.map {
                    val russianRoulette = Random.nextInt(0, marsImages.lastIndex)
                    it.copy(
                        imgSrcUrl = marsImages[russianRoulette],
                        description = descriptions[russianRoulette]
                    )
                }
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateListFilter(type: MarsApiFilter) {
        getMarsRealEstateData(type)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onSelectedItemNavigate(marsProperty: MarsProperty) {
        Log.i("viewmodel", "heya == $marsProperty.id")
        _selectedItemData.value = marsProperty
    }

    fun onSelectedItemNavigateComplete() {
        _selectedItemData.value = null
    }

    private val marsImages = listOf(
        "https://akm-img-a-in.tosshub.com/indiatoday/images/media_bank/202309/why-is-mars-called-the-red-planet-151828846-16x9.jpg?VersionId=AJNTpbOFLtFljuT59lmMlkMbgoZlT0_4&size=690:388",
        "https://awsimages.detik.net.id/visual/2023/05/17/skrinkle-haven-mars_169.jpeg?w=650",
        "https://c.files.bbci.co.uk/9382/production/_120526773_whatsubject.jpg",
        "https://awsimages.detik.net.id/visual/2021/02/28/penjelajah-mars-perseverance-nasa-memperoleh-gambar-ini-menggunakan-kamera-right-mastcam-z-di-mars-nasa-jpl-caltech-asu_169.png?w=650",
        "https://upload.wikimedia.org/wikipedia/commons/d/dc/PIA17944-MarsCuriosityRover-AfterCrossingDingoGapSanddune-20140209.jpg",
        "https://asset.kompas.com/crops/jBwfnLdqPCFfxrmX4gM8GyS1F5s=/1x0:1024x682/1200x800/data/photo/2021/10/11/6163aaf342b37.jpg",
        "https://www.vaisala.com/sites/default/files/styles/16_9_liftup_extra_large/public/images/LIFT-Mars_3D-illustration_1600x900.jpg?itok=iAJOaL3A",
        "https://th-thumbnailer.cdn-si-edu.com/nt7DMFeotUrOeng0GROnSTepABE=/fit-in/1600x0/https%3A%2F%2Ftf-cmsv2-smithsonianmag-media.s3.amazonaws.com%2Ffiler%2Fb8%2F85%2Fb8855ec1-9695-478d-b796-6130d6f32ff8%2F12g_on2014_mastcamintovalley_live-1.jpg",
        "https://media.npr.org/assets/img/2023/01/28/mars-bear_wide-b17401ee16ccf5c18611d4f39b37ea921f985e51-s1400-c100.jpeg",
        "https://globalnews.ca/wp-content/uploads/2014/01/mars-tracks.jpg?quality=85&strip=all",
        "https://www.universetoday.com/wp-content/uploads/2015/01/mars_coffin_original-e1421190770698.jpg",
        "https://c.files.bbci.co.uk/F7BB/production/_117591436_pia19839-galecrater-main.png"
    );
    private val descriptions = listOf(
        "<b>Rusty Expanse</b>: An endless vista of ochre-colored terrain stretches across the Martian surface, marked by expansive plains punctuated by towering, wind-sculpted mesas. The rusty, iron-rich soil creates a surreal landscape reminiscent of a vast, ancient desert.",
        "<b>Frozen Valleys</b>: Deep, shadowy valleys carve through the landscape, where remnants of frozen water lurk beneath a thin layer of dust. The icy formations give a stark contrast to the surrounding barrenness, with cliffs and slopes glistening in the faint sunlight.",
        "<b>Cratered Highlands</b>: Rugged and cratered highlands dominate the horizon, displaying a maze of impact scars from ancient collisions. Peaks and ridges stand in stark relief against the sky, while winds whip through the rocky outcrops, sculpting the landscape over eons.",
        "<b>Dust Storm Plains</b>: Wide, flat expanses covered in fine reddish-brown dust stretch as far as the eye can see. Occasional dust devils dance across this desolate terrain, casting swirling patterns against the horizon during seasonal storms.",
        "<b>Volcanic Badlands</b>: An otherworldly expanse of volcanic badlands features dark, ancient lava flows interspersed with rugged, blackened rock formations. The landscape is marked by the remnants of long-dormant volcanoes, their peaks weathered by time and erosion.",
        "<b>Salt Flats and Dunes</b>: Vast salt flats shimmer in the sunlight, contrasting with undulating sand dunes that weave across the landscape. The shifting sands form intricate patterns while the salt flats sparkle like a crystalline mosaic.",
        "<b>Canyons of Erosion</b>: Deep canyons carved by ancient rivers, now long dry, cut through the terrain, revealing layers of sedimentary rock. Over time, these water-formed chasms have been reshaped by wind erosion, creating a surreal and intricate maze of cliffs and buttes.",
        "<b>Methane Lakes</b>: In certain regions, small methane lakes reflect the dim Martian sky, creating pools of iridescent hues amid the desolate surroundings. The lakes are a curious sight, hinting at the planet's complex geological history and potential for past or present life.",
        "<b>Polar Ice Caps</b>: At the poles, expansive ice caps shimmer under the diffuse Martian sunlight, alternating between frozen carbon dioxide and water ice. Jagged cliffs overlook plains of frozen terrain, creating a breathtaking vista of white against the reddish-brown backdrop.",
        "<b>Alien Plateaus</b>: Elevated plateaus rise majestically above the Martian surface, featuring peculiar rock formations eroded by ancient winds. These surreal landscapes present a mosaic of colors, with hints of greenish hues created by minerals reacting with the Martian atmosphere.",
        "<b>Regolith Ripples</b>: Across vast plains, the Martian regolith forms delicate, mesmerizing ripples that stretch to the horizon. These undulating patterns, created by wind erosion and occasional dust storms, give the landscape an otherworldly texture, resembling waves frozen in time.",
        "<b>Cavernous Networks</b>: Beneath the surface, an intricate network of caverns and underground tunnels winds its way through the Martian crust. Carved out by ancient lava flows and geological processes, these subterranean passages remain hidden, holding potential secrets of Mars' geological evolution."
    )
}
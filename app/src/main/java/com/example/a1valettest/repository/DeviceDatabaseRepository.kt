package com.example.a1valettest.repository

import androidx.room.withTransaction
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeviceDatabaseRepository @Inject constructor(
    private val deviceDataBase: DeviceDataBase,
    private val deviceDao: DeviceDao
) : Repository.DeviceDataBaseRepository {

    /* Guess that we are fetching from internet and save it to local DB
     and handle all this with Coroutine/Flow for multi-threading and collecting items
    */
    // All Devices
    override fun deviceContentDataSource(): List<DeviceContent> =
        buildList {
            add(
                DeviceContent(
                    id = "1",
                    os = "Android 12",
                    status = "Sold",
                    price = 800,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
                    title = "Samsung Galaxy S21",
                    description = "The Samsung Galaxy S21+ 5G comes with a 6.7 inch touchscreen with 394PPI. It packs a 64-megapixel rear camera and a 10-megapixel selfie-camera. This is all powered by the Exynos 2100 International chipset and 8GB of RAM.",
                    company = "Samsung"
                )
            )
            add(
                DeviceContent(
                    id = "2",
                    os = "Android 11",
                    status = "Available",
                    price = 700,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://oneclickroot.com/wp-content/uploads/2020/07/samsung-galaxy-a50.png",
                    title = "Samsung Galaxy A50",
                    description = "Capture more fabulousness with the revolutionary 123° ultra-wide lens. Capture more in every moment with 240FPS slow-mo video. Redefine your visual experience with the Super AMOLED 16.21cm (6.4\") FHD+ Infinity-U display. Unlock coolness with the on-screen fingerprint sensor. Power up your phone in no time with 15W Fast Charging. Play games, stream videos, and vlog - all at the same time!.",
                    company = "Samsung"
                )
            )
            add(
                DeviceContent(
                    id = "3",
                    os = "Android 12",
                    status = "Available",
                    price = 555,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://cdn.vodafone.co.uk/en/assets/images/desktop/Samsung_Galaxy_A12_black-full-product-front-600.png",
                    title = "Samsung Galaxy A12",
                    description = "Stunning Infinity-V display Watch the latest TV shows and movies on the Samsung Galaxy A12. Colours and picture details are brought to life on the HD+ display. Its 6.5” design gives you maximum screen size while being small enough to hold in one hand. That means you can stream the latest episode of your favourite show during your morning commute. Flawless photos and selfies Take crisp and clear photos with the quad main cameras.",
                    company = "Samsung"
                )
            )
            add(
                DeviceContent(
                    id = "4",
                    os = "Android 12",
                    status = "Available",
                    price = 720,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://i2.wp.com/mobilize.com.pk/wp-content/uploads/2021/04/mi-11-lite.png?fit=600%2C600&ssl=1",
                    title = "Xiaomi Mi 11",
                    description = "Xiaomi Mi 11 mobile was launched on 28th December 2020. The phone comes with a 6.81-inch touchscreen display with a resolution of 1440x3200 pixels at a pixel density of 515 pixels per inch (ppi) and an aspect ratio of 20:9. Xiaomi Mi 11 is powered by an octa-core Qualcomm Snapdragon 888 processor.",
                    company = "Xiaomi"
                )
            )
            add(
                DeviceContent(
                    id = "5",
                    os = "Android 12",
                    status = "Available",
                    price = 900,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://i01.appmifile.com/webfile/globalimg/products/pc/mi-11-ultra/pc-specs-header.png",
                    title = "Xiaomi Mi 11 Ultra",
                    description = "If someone told us the Xiaomi Mi 11 Ultra was a parody of the Samsung Galaxy S21 Ultra and iPhone 12 Pro Max-style superphones, we'd probably believe them. Its feature set and design evoke those luxury phones, but it turns it up to 11 with one extra feature that seems almost sarcastic.",
                    company = "Xiaomi"
                )
            )
            add(
                DeviceContent(
                    id = "6",
                    os = "Android 12",
                    status = "Unavailable",
                    price = 1100,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://images.samsung.com/is/image/samsung/p6pim/uk/galaxy-s21/gallery/uk-galaxy-s21-ultra-5g-g988-sm-g998bzsheua-thumb-368888073",
                    title = "Samsung Note 21 Ultra",
                    description = "After months of leaks and speculation it's official: the Samsung Galaxy Note 21 is canceled. That news comes direct from TM Roh, Samsung's President and Head of Mobile Communications, so it doesn't get much more official than that.",
                    company = "Samsung"
                )
            )
            add(
                DeviceContent(
                    id = "7",
                    os = "Android 12",
                    status = "Available",
                    price = 199,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://consumer.huawei.com/content/dam/huawei-cbg-site/common/mkt/pdp/phones/p-smart-2021/list-img/green-list.png",
                    title = "Huawei P Smart 2021",
                    description = "Design Love at First Sight With refined craftsmanship and exquisite coating, HUAWEI P smart 2021's elegant look combined with curved edges offers a comfortable touch in your hands. The three dynamic colours shine in the light and let you love it at the first sight.",
                    company = "Huawei"
                )
            )
            add(
                DeviceContent(
                    id = "8",
                    os = "Android 12",
                    status = "Available",
                    price = 269,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://consumer.huawei.com/content/dam/huawei-cbg-site/common/mkt/list-image/phones/p40-pro/p40-pro-silver.png",
                    title = "Huawei P40 Pro",
                    description = "Visionary photography - speak for yourself with the Ultra Vision Leica Quad Camera by capturing photos and videos anytime and anywhere you want. Revolutionise your experience of speed and power with the cutting-edge Kirin 990 5G Chipset. The innovative design upgrades your visual entertainment and ergonomic comfort. Explore now and future with HUAWEI P40 Pro.",
                    company = "Huawei"
                )
            )
            add(
                DeviceContent(
                    id = "9",
                    os = "Android 12",
                    status = "Available",
                    price = 899,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://www.androidcentral.com/sites/androidcentral.com/files/styles/large/public/article_images/2021/10/google-pixel-6-and-6-pro-render.png",
                    title = "Google Pixel 6 Pro",
                    description = "The best of Google built around you. Google Tensor Our first custom-built processor. The first processor designed by Google and made for Pixel Google Tensor makes the new Pixel phones our most powerful yet. Googles advanced on-device AI for features that only Google can deliver. Built in best-in-class computational photography for amazing photos and videos. Integrated industry-leading security for added protection. Up to 80 faster performance for speed and responsiveness.",
                    company = "Google"
                )
            )
            add(
                DeviceContent(
                    id = "10",
                    os = "Android 12",
                    status = "Available",
                    price = 903,
                    currency = "USD",
                    isFavorite = false,
                    imageUrl = "https://static-www.o2.co.uk/sites/default/files/sony-xperia-1-III-sku-header-300721.png",
                    title = "Sony Xperia 1",
                    description = "The Xperia 1 was made with the advanced technology from Sony's professional monitors, cameras, and audio devices, to deliver the best experience in a smartphone. It features the world's first 21:9 CinemaWide 4K HDR OLED display and a pro-quality triple lens camera.",
                    company = "Sony"
                )
            )
        }

    override suspend fun insertAllDevices(deviceContentList: List<DeviceContent>?) =
        deviceDao.insertAllDevices(deviceContentList = deviceContentList)

    override fun getAllDevices(): Flow<List<DeviceContent>> = deviceDao.getAllDevices()

    override suspend fun updateDevice(deviceContent: DeviceContent?) =
        deviceDao.updateDeviceContent(deviceContent = deviceContent)


    // My Devices
    override fun getMyDevices(isFavorite: Boolean) =
        deviceDao.getAllMyDevices(isFavorite = isFavorite)

    override suspend fun insertToMyDevice(myDeviceContent: MyDeviceContent?) =
        deviceDataBase.withTransaction {
            deviceDao.insertToMyDevice(myDeviceContent = myDeviceContent)
        }

    override suspend fun deleteDevice(myDeviceContent: MyDeviceContent?) =
        deviceDao.deleteDevice(myDeviceContent = myDeviceContent)

}
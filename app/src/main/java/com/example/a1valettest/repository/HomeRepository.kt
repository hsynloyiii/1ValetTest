package com.example.a1valettest.repository

import androidx.room.withTransaction
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.DeviceResponse
import com.example.a1valettest.utils.di.MainDispatchers
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    @MainDispatchers private val mainDispatchers: CoroutineDispatcher,
    private val deviceDataBase: DeviceDataBase,
    private val deviceDao: DeviceDao
) {

    /* We guess that we are fetching from internet and save it to local DB
    and handle all this with Coroutine/Flow for multi-threading and collecting items
     */
    suspend fun deviceContentDataSource() {
        deviceDao.getAllDevices().collect {
            if (it.isEmpty()) {
                val devices = withContext(mainDispatchers) {
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
                                description = ""
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
                                imageUrl = "https://toppng.com/uploads/preview/samsung-galaxy-a50-samsung-galaxy-a50-2019-11562961823yplum8mlj9.png",
                                title = "Samsung Galaxy A50",
                                description = ""
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
                                description = ""
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
                                description = ""
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
                                description = ""
                            )
                        )
                        add(
                            DeviceContent(
                                id = "6",
                                os = "Android 12",
                                status = "Available",
                                price = 1100,
                                currency = "USD",
                                isFavorite = false,
                                imageUrl = "https://images.samsung.com/is/image/samsung/p6pim/uk/galaxy-s21/gallery/uk-galaxy-s21-ultra-5g-g988-sm-g998bzsheua-thumb-368888073",
                                title = "Samsung Note 21 Ultra",
                                description = ""
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
                                description = ""
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
                                description = ""
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
                                description = ""
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
                                description = ""
                            )
                        )
                    }
                }
                deviceDataBase.withTransaction {
                    deviceDao.insertAllDevices(deviceContentList = DeviceResponse(devices = devices).devices)
                }
            }
        }
    }

    val getAllDevices = deviceDao.getAllDevices()

}
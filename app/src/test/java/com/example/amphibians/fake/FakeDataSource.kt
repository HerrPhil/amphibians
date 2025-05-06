package com.example.amphibians.fake

import com.example.amphibians.network.AmphibianPhoto

object FakeDataSource {

    const val nameOne = "img1"
    const val nameTwo = "img2"

    const val typeOne = "type1"
    const val typeTwo = "type2"

    const val descriptionOne = "description1"
    const val descriptionTwo = "description2"

    const val imgOne = "url.1"
    const val imgTwo = "url.2"

    val photosList = listOf(
        AmphibianPhoto(
            name = nameOne,
            type = typeOne,
            description = descriptionOne,
            imgSrc = imgOne
        ),
        AmphibianPhoto(
            name = nameTwo,
            type = typeTwo,
            description = descriptionTwo,
            imgSrc = imgTwo
        )
    )

}
package ir.heydarii.imagefinder.pojos

data class ImageSearchResponseModel(
    val data: List<Data>,
    val page: Int,
    val per_page: Int,
    val search_id: String,
    val total_count: Int
)

data class Data(
    val aspect: Double,
    val assets: Assets,
    val contributor: Contributor,
    val description: String,
    val id: String,
    val image_type: String,
    val media_type: String,
    val url: String
)

data class Assets(
    val huge_thumb: HugeThumb,
    val large_thumb: LargeThumb,
    val preview: Preview,
    val small_thumb: SmallThumb
)

data class HugeThumb(
    val height: Int,
    val url: String,
    val width: Int
)

data class LargeThumb(
    val height: Int,
    val url: String,
    val width: Int
)

data class Preview(
    val height: Int,
    val url: String,
    val width: Int
)

data class SmallThumb(
    val height: Int,
    val url: String,
    val width: Int
)

data class Contributor(
    val id: String
)
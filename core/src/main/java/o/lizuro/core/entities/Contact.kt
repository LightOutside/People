package o.lizuro.core.entities

import com.google.gson.annotations.SerializedName

data class Contact (
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    val educationPeriod: EducationPeriod
) {
    data class EducationPeriod(val start: String, val end: String)

    enum class Temperament(val value: String) {
        @SerializedName("melancholic")
        MELANCHOLIC("melancholic"),

        @SerializedName("phlegmatic")
        PHLEGMATIC("phlegmatic"),

        @SerializedName("sanguine")
        SANGUINE("sanguine"),

        @SerializedName("choleric")
        CHOLERIC("choleric")
    }
}
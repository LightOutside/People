package o.lizuro.core.repo

import com.google.gson.annotations.SerializedName
import java.util.*

enum class Temperament(val value: String) {
    @SerializedName("vehicle")
    MELANCHOLIC("melancholic"),

    @SerializedName("phlegmatic")
    PHLEGMATIC("phlegmatic"),

    @SerializedName("sanguine")
    SANGUINE("sanguine"),

    @SerializedName("choleric")
    CHOLERIC("choleric")
}

data class EducationPeriod(val start: Date, val end: Date)

data class Contact (
    val id: String,
    val name: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    val educationPeriod: EducationPeriod
)
import androidx.exifinterface.media.ExifInterface
import java.io.InputStream

fun InputStream.readExifOrientation(): Int {
    return ExifInterface(this).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
}

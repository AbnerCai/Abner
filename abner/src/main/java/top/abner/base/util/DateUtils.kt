package top.abner.base.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/27 20:39
 */
@RequiresApi(Build.VERSION_CODES.O)
object DateUtils {

    /**
     * 获取今天日期.
     *
     * */
    fun getCurrentDate(): String {
        return LocalDate.now().toString()
    }

    /**
     * 获取当前时间.
     * */
    fun getCurrentTime(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return LocalTime.now().format(formatter)
    }

    /**
     * 获取当前日期和时间.
     * */
    fun getCurrentDateTime(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.now().format(formatter)
    }

    /**
     * 比较两个日期大小
     * @param newDateStr 新的时间
     * @param oldDateStr 旧的时间
     * @return true 新时间 > 旧时间
     * */
    fun compareDate(newDateStr: String, oldDateStr: String): Boolean? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        try {
            val newDate = LocalDate.parse(newDateStr, formatter)
            val oldDate = LocalDate.parse(oldDateStr, formatter)
            return newDate.isAfter(oldDate)
        } catch (e: Exception) {
            return null
        }
    }
}
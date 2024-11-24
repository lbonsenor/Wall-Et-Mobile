package com.example.wall_et_mobile.data.model

import com.example.wall_et_mobile.R
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


enum class FilterDateType (
    val labelInt: Int,
    val minDate: LocalDate)
{
    TODAY(R.string.today, LocalDate.now()),
    LAST_WEEK(R.string.last_week, LocalDate.now().minusDays(7)),
    LAST_MONTH(R.string.last_month, LocalDate.now().minusMonths(1)),
    LAST_YEAR(R.string.last_year, LocalDate.now().minusYears(1)),
    MAX(R.string.max, LocalDate.MIN);

    fun inRange(date: Date): Boolean{
        return minDate.isBefore(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || minDate.equals(date)
    }
}
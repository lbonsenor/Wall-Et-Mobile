package com.example.wall_et_mobile.model

import java.time.LocalDate
import com.example.wall_et_mobile.R


enum class FilterDateType (
    val labelInt: Int,
    val minDate: LocalDate)
{
    TODAY(R.string.today, LocalDate.now()),
    LAST_WEEK(R.string.last_week, LocalDate.now().minusDays(7)),
    LAST_MONTH(R.string.last_month, LocalDate.now().minusMonths(1)),
    LAST_YEAR(R.string.last_year, LocalDate.now().minusYears(1)),
    MAX(R.string.max, LocalDate.MIN);

    fun inRange(date: LocalDate): Boolean{
        return minDate.isBefore(date) || minDate == date
    }
}
package com.otakkanan.taskapp.injection.provide

import com.otakkanan.taskapp.ui.main.kalender.CalendarAdapter
import com.otakkanan.taskapp.ui.main.kalender.CalendarHeaderAdapter
import com.otakkanan.taskapp.ui.main.kalender.DataAdapter
import com.otakkanan.taskapp.utils.Helper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    @FragmentScoped
    fun provideDataDiff(): DataAdapter.DataDiff = DataAdapter.DataDiff()

    @Provides
    @FragmentScoped
    fun provideDataAdapter(dataDiff: DataAdapter.DataDiff): DataAdapter = DataAdapter(dataDiff)

    @Provides
    @FragmentScoped
    fun provideCalendarHeaderAdapter(): CalendarHeaderAdapter = CalendarHeaderAdapter(Helper.daysOfWeekFromLocale())

    @Provides
    @FragmentScoped
    fun provideCalendarAdapter(): CalendarAdapter = CalendarAdapter()
}
package com.example.firstapp

import android.content.Context

/*
 We are going to use this class to explicitly enumerate the different
 temperature settings available.
 */
enum class TempDisplaySetting
{
    // Now we will be able to use explicitly whether we are using F or C
    Fahrenheit, Celsius
}


/*
 TempDisplaySettingsManager is going to reference shared preferences to
 save the TempDisplaySetting value. To do that we need to pass in the context
 to this class bcoz we need a context to get reference to shared preferences.
 And in class body we will use that context parameter to get aa reference to
 shared preference object. Context.MODE_PRIVATE because we want this to be
 private to our app. This call to getSharedPreferences is opening up a file
 on disk or creating if it doesn't exist and its going to allow us to
  writeout keyvalues pairs of data to this file using simple api. And this is
  kind of default simple way of storing small bits of data that you want to
  persist across application restart
 */


class TempDisplaySettingsManager(context: Context )
{

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    /*
    We are going to pass in a TempDisplaySetting value. This is going to
    let us call updateSetting and we will pass in either F or C to indicate
    how we want the temp to be displayed. So when user call updateSetting
    we are going to update our preferences with that setting value
     */
    fun updateSetting(setting: TempDisplaySetting)
    {
        /*
        Edit will start us into an edit mode that we can use to edit the
        preferences. Once we are in the edit mode we can put diff type of data.
        Any enums in kotlin have name property that matches how the class name
        is defined. Commit will immediately write our changes on disk while apply
        will do in the background. Because its a small thing so commit should be
        fine in this case
         */
        preferences.edit().putString("key_temp_display", setting.name).commit()

    }

    /*
    So now we have given the user a way to update settings we need a way to retrieve
    the settings. So we will have a method for that called getTempDisplaySetting().
    Its going to return an instance of TempDisplaySetting (Fahrenheit or celsius)
    To get that value we are going to create a variable settingValue.
    TempDisplaySetting.Fahrenheit.name cuz we need a string value rather than enum value

     */


    fun getTempDisplaySetting() : TempDisplaySetting
    {
        // ?: operator means if everything on left side null, return right side
        val settingValue = preferences.getString("key_temp_display",TempDisplaySetting.Fahrenheit.name) ?:TempDisplaySetting.Fahrenheit.name
        /*
        TempDisplaySetting.valueOf is making use of method of
        an enum which basically says I can pass in string to valueOf and if
        that string matches the name of one of the enum types in our case
        Fahrenheit or celsius then it will basically return you back the
        instance of that. So if I pass in the String Fahrenheit, I will get
        back an instance of Fahrenheit temp display setting. So this is what
        is basically mapping String version of the name of the setting to the
        actual static enum type

         */
        return TempDisplaySetting.valueOf(settingValue)
    }
}
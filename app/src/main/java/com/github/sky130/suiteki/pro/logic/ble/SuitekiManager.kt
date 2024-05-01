package com.github.sky130.suiteki.pro.logic.ble

import android.util.ArrayMap
import android.util.Log
import com.github.sky130.suiteki.pro.MainApplication.Companion.context
import com.github.sky130.suiteki.pro.logic.database.model.Device
import com.github.sky130.suiteki.pro.util.BytesUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow

object SuitekiManager {
    private val flow = MutableStateFlow<AbstractSuitekiDevice?>(null)
    val bleDevice: Flow<AbstractSuitekiDevice?> get() = flow
    private val classMap = ArrayMap<String, Pair<Suiteki, Class<out AbstractSuitekiDevice>>>()
    val logList = mutableListOf<String>()

    fun log(vararg str: Any) {
        logList.add(str.joinToString("\n") {
            if (it is ByteArray) {
                BytesUtils.bytesToHexStr(it).toString()
            } else {
                it.toString()
            }
        })
    }

    fun connect(device: Device) {
        for ((i, p) in classMap) {
            if (i.toRegex().matches(device.name)) {
                flow.value = p.second.getConstructor(
                    String::class.java,
                    String::class.java,
                    String::class.java,
                ).newInstance(device.name, device.mac, device.key).apply {
                    onStart()
                }
                break
            }
        }
    }

    fun init() {
        ClassesReader.reader("com.github.sky130.suiteki.pro", context)
            .filter { !it.name.contains("$") }.forEach {
                for (i in it.annotations) {
                    if (i is Suiteki) {
                        Log.d("TAG", i.pattern)
                        classMap[i.pattern] = i to (it as Class<AbstractSuitekiDevice>)
                    }

                }
            }
    }

}
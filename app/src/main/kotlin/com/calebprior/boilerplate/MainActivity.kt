package com.calebprior.boilerplate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.calebprior.ExampleAnnotation
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.nobleworks_software.injection.android.kotlin.inject
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AnkoLogger {

    @Inject
    lateinit var flowController: FlowController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        inject()

        GeneratedByProcessorInKotlin.main(arrayOf("test"))

        val router = Conductor.attachRouter(this, controller_container, savedInstanceState)
        flowController.attachRouter(router)

        flowController.launchHomeScreen()
    }

    override fun onBackPressed() {
        if (! flowController.onBackPressed()) {
            super.onBackPressed()
        }
    }
}

@ExampleAnnotation
class test2 {

}
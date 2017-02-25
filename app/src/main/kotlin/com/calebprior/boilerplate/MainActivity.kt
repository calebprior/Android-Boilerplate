package com.calebprior.boilerplate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
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

        val router = Conductor.attachRouter(this, controller_container, savedInstanceState)
        flowController.attachRouter(router)

        flowController.launchHomeScreen()
    }
}
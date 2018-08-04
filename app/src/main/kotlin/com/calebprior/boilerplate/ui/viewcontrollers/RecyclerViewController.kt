package com.calebprior.boilerplate.ui.viewcontrollers

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.calebprior.boilerplate.R
import com.calebprior.boilerplate.adapters.FlexibleAdapter
import com.calebprior.boilerplate.adapters.TestHolder
import com.calebprior.boilerplate.data.Character
import com.calebprior.boilerplate.ui.base.BasePresenter
import com.calebprior.boilerplate.ui.base.BaseView
import com.calebprior.boilerplate.ui.base.BaseViewController
import com.calebprior.boilerplate.ui.contracts.RecyclerExampleContract
import com.jakewharton.rxbinding.view.clicks
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import okhttp3.OkHttpClient
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import org.jetbrains.anko.onClick
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.function.Consumer
import android.support.v7.widget.RecyclerView as ARecyclerView


class RecyclerViewController(
        args: Bundle? = null
) : BaseViewController<RecyclerExampleContract.Presenter, RecyclerExampleContract.View>(args), RecyclerExampleContract.View {

    override fun viewContent() = R.layout.view_recycler

    override fun subscriptionMappings() = mapOf(
            find<FloatingActionButton>(R.id.floatingActionButton).clicks() to { presenter.onButtonPressed() }
    )

    val flexibleAdapter = FlexibleAdapter<FlexibleAdapter.FlexibleHolder<*>>()

    override fun onViewBound(view: View) {
        find<ARecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = flexibleAdapter
            itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f)).apply {
                addDuration = 500
            }
        }
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        presenter.loadData()
    }

    override fun addItem(item: FlexibleAdapter.FlexibleHolder<*>) {
        flexibleAdapter.addItem(item)
    }

    override fun removeItem(item: FlexibleAdapter.FlexibleHolder<*>) {
        flexibleAdapter.removeItem(item)
    }
}


class TestViewController(
        args: Bundle? = null
) : BaseViewController<BasePresenter<BaseView>, BaseView>(args), BaseView {

    override fun viewContent() = R.layout.view_recycler

    val flexibleAdapter = FlexibleAdapter<FlexibleAdapter.FlexibleHolder<*>>()

    override fun onViewBound(view: View) {
        find<ARecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = flexibleAdapter
            itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f)).apply {
                addDuration = 500
            }
        }

        val okHttpClient = OkHttpClient()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        val redditApi = retrofit.create(StarWarsApi::class.java)

        Observable.from(1..10).concatMap {
            redditApi.getCharacter(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val a = Holder(it)
                    a.setSubscription(Consumer {
                                info { "Test HERE" }
                            }
                    )
                    flexibleAdapter.addItem(
                            a
                    )
                }
    }
}

interface StarWarsApi {
    @GET("people/{personId}")
    fun getCharacter(@Path("personId") id: Int): Observable<Character>
}

class Holder(
        val test: Character
) : FlexibleAdapter.FlexibleHolder<Holder> {

    lateinit var clickListener: Consumer<Holder>

    override fun setSubscription(observable: Consumer<Holder>) {
        clickListener = observable
    }

    override fun displayView(rootView: View) {
        rootView.find<TextView>(R.id.item_text).text = test.name

        rootView.find<CardView>(R.id.card_view).onClick {
            clickListener.accept(this)
        }
    }

    override fun getLayout() = R.layout.item_test
}
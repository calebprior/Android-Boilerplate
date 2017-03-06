package com.calebprior.boilerplate.ui.transisitions

import android.annotation.TargetApi
import android.os.Build
import android.support.v4.view.GravityCompat
import android.transition.*
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandler
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.childrenSequence


class SharedSlideChangeHandlerCompat(
        reverse: Boolean = false,
        leftToRight:Boolean = true
) : TransitionChangeHandlerCompat(
        ArcFadeMoveChangeHandler(reverse, leftToRight),
        FadeChangeHandler()
) {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class ArcFadeMoveChangeHandler(val reverse: Boolean = false, var leftToRight:Boolean = true) : TransitionChangeHandler(), AnkoLogger {

        override fun getTransition(container: ViewGroup, from: View?, to: View?, isPush: Boolean): Transition {

            val outgoingSlide = Slide()
            outgoingSlide.duration = 500

            val incomingSlide = Slide()
            incomingSlide.duration = 500

            if(leftToRight) {
                outgoingSlide.slideEdge = GravityCompat.getAbsoluteGravity(GravityCompat.START, container.layoutDirection)
                incomingSlide.slideEdge = GravityCompat.getAbsoluteGravity(GravityCompat.END, container.layoutDirection)
            } else {
                outgoingSlide.slideEdge = Gravity.TOP
                incomingSlide.slideEdge = Gravity.BOTTOM
            }

            var overlapSharedViews = emptyList<View>()

            from?.childrenSequence()?.forEach {
                if (it.transitionName == null) {
                    if (isPush && ! reverse) {
                        outgoingSlide.addTarget(it)
                    } else {
                        incomingSlide.addTarget(it)
                    }
                } else {
                    overlapSharedViews = overlapSharedViews.plus(it)
                }
            }

            to?.childrenSequence()?.forEach {
                if (it.transitionName == null) {
                    if (isPush && ! reverse) {
                        incomingSlide.addTarget(it)
                    } else {
                        outgoingSlide.addTarget(it)
                    }
                } else {
                    val view = it

                    if (overlapSharedViews.count { it.transitionName == view.transitionName } == 0) {
                        if (isPush && ! reverse) {
                            incomingSlide.addTarget(it)
                        } else {
                            outgoingSlide.addTarget(it)
                        }

                        overlapSharedViews.filterNot { it.transitionName == view.transitionName }.forEach {
                            if (isPush && ! reverse) {
                                outgoingSlide.addTarget(it)
                            } else {
                                incomingSlide.addTarget(it)
                            }
                        }
                    }
                }
            }

            if (isPush && ! reverse) {
                val transition = TransitionSet()
                        .setOrdering(TransitionSet.ORDERING_SEQUENTIAL)
                        .addTransition(outgoingSlide)
                        .addTransition(TransitionSet().addTransition(ChangeBounds()).addTransition(ChangeClipBounds()).addTransition(ChangeTransform()))
                        .addTransition(incomingSlide)

                transition.pathMotion = ArcMotion()

                return transition

            } else {
                val transition = TransitionSet()
                        .setOrdering(TransitionSet.ORDERING_SEQUENTIAL)
                        .addTransition(incomingSlide)
                        .addTransition(TransitionSet().addTransition(ChangeBounds()).addTransition(ChangeClipBounds()).addTransition(ChangeTransform()))
                        .addTransition(outgoingSlide)

                transition.pathMotion = ArcMotion()

                return transition
            }
        }

    }
}
package com.projetos.appfinanceiro.integration.otel.tracer

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import java.util.WeakHashMap

object ActivityNavigationTracer : Application.ActivityLifecycleCallbacks {

    val tracer: Tracer? = TracerOpenTelemetryUtil.getTracer()

    val spanMap = WeakHashMap<Activity, Span>()


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d("OTEL_DEBUG", "ActivityNavigationTracer: onActivityCreated para ${activity.javaClass.simpleName}")
        startAutoTracer(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d("OTEL_DEBUG", ">>>> onActivityStarted: ${activity.javaClass.simpleName}")
        startAutoTracer(activity)
    }
    override fun onActivityResumed(activity: Activity) {
        Log.d("OTEL_DEBUG", ">>>> onActivityResumed: ${activity.javaClass.simpleName}")
        spanMap[activity]?.addEvent("activity_resumed")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d("OTEL_DEBUG", ">>>> onActivityPaused: ${activity.javaClass.simpleName}")
        spanMap[activity]?.addEvent("activity_paused")
        spanMap[activity]?.setAttribute(AttributeKey.stringKey("app.resume"), "Login_screen")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d("OTEL_DEBUG", ">>>> onActivityStopped: ${activity.javaClass.simpleName}")
        finalizerTracer(activity)
    }
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d("OTEL_DEBUG", ">>>> onActivitySaveInstanceState: ${activity.javaClass.simpleName}")
        finalizerTracer(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d("OTEL_DEBUG", "ActivityNavigationTracer: onActivityDestroyed para ${activity.javaClass.simpleName}")
        finalizerTracer(activity)
    }


    private fun startAutoTracer(activity: Activity){
        tracer ?: run {
            return
        }

        val spanName = "navigation: ${activity.javaClass.simpleName}"
        val span = tracer.spanBuilder(spanName).startSpan()

        span.setAttribute("navigation.activity.name", activity.javaClass.name)

        activity.intent?.extras?.let { extras ->
            // Cuidado com dados sensíveis nos extras! Filtre se necessário.
            extras.keySet().forEach { key ->
                span.setAttribute("navigation.intent.extra.$key", extras.get(key).toString())
            }
        }

        span.addEvent("activity_started")

        spanMap[activity] = span
    }

    private fun finalizerTracer(activity: Activity){
        val span = spanMap[activity]
        span?.let {
            it.addEvent("activity_destroyed")
            it.end()

            spanMap.remove(activity)
            TracerOpenTelemetryUtil.flush()
        }
    }


}
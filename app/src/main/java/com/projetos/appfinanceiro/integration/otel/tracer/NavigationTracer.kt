package com.projetos.appfinanceiro.integration.otel.tracer

import androidx.navigation.NavController
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.common.Attributes

object NavigationTracer {

    fun initialize(navController: NavController) {
        val tracer: Tracer = TracerOpenTelemetryUtil.getTracer() ?: return

        val listener = NavController.OnDestinationChangedListener { _, destination, arguments ->
            val spanName = "navigation: ${destination.route ?: destination.label}"
            val span = tracer.spanBuilder(spanName).startSpan()

            try {
                // Adiciona atributos úteis ao span para detalhar o evento
                span.setAttribute("navigation.destination.id", destination.id.toLong())
                destination.route?.let { span.setAttribute("navigation.destination.route", it) }
                destination.label?.let { span.setAttribute("navigation.destination.label", it.toString()) }

                // Cuidado com informações sensíveis nos argumentos!
                // Considere filtrar ou anonimizar os dados.
                arguments?.let { args ->
                    val attributesBuilder = Attributes.builder()
                    args.keySet().forEach { key ->
                        attributesBuilder.put("navigation.argument.$key", args.get(key).toString())
                    }
                    span.addEvent("navigation_arguments", attributesBuilder.build())
                }

                span.addEvent("destination_changed")

            } finally {
                span.end()
            }
        }

        navController.addOnDestinationChangedListener(listener)
    }
}
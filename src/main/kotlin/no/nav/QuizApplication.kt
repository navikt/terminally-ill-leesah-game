package no.nav

import no.nav.db.Database
import no.nav.quizrapid.*
import no.nav.rapid.Assessment
import no.nav.rapid.Question


/**
 * QuizApplication
 *
 * Her skal teamet bygge ut funksjonalitet for å løse oppgavene i leesah-game.
 */
class QuizApplication(private val teamName: String, database: Database? = null): QuizParticipant(teamName) {

    override fun handle(question: Question) {
        logger.log(question)
        if (question.category == "team-registration") handleRegisterTeam(question)
        if (question.category == "arithmetic") handleArithmetic(question)
        if (question.category == "make-ingress") handleIngress(question)
        if (question.category == "NAV") handleNAV(question)

    }


    override fun handle(assessment: Assessment) {
        logger.log(assessment)
    }

    /**
     * Spørsmål handlers
     */

    private fun operatorFromChar(charOperator: Char):(Int, Int)->Int
    {
        return when(charOperator)
        {
            '+'->{a,b->a+b}
            '-'->{a,b->a-b}
            '/'->{a,b->a/b}
            '*'->{a,b->a*b}
            else -> throw Exception("That's not a supported operator")
        }
    }

    private fun handleArithmetic(question: Question) {
        var lists = question.question.split(" ")
        var myAnswer = operatorFromChar(lists[1].toCharArray()[0]).invoke(lists[0].toInt(), lists[2].toInt())
        answer(question.category, question.messageId, myAnswer.toString())
    }

    private fun handleIngress(question: Question) {
        answer(question.category, question.messageId,"https://terminally-ill-leesah-quiz.dev.intern.nav.no")
    }

    private fun handleNAV(question: Question) {
        var answerMap = hashMapOf<String, String>()
        answerMap.put("På hvilken nettside finner man informasjon om rekruttering til NAV IT?", "https://www.detsombetyrnoe.no/")
        answerMap.put("Hva heter applikasjonsplattformen til NAV?", "NAIS")
        answer(question.category, question.messageId, answerMap[(question.question)]!!)
    }


    private fun handleRegisterTeam(question: Question) {
        logger.log(question)
        answer(question.category, question.messageId,  teamName)
    }

}
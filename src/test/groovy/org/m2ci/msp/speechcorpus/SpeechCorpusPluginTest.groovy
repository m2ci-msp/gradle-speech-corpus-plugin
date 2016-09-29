package org.m2ci.msp.speechcorpus

import org.testng.annotations.*

import org.gradle.testfixtures.ProjectBuilder

class SpeechCorpusPluginTest {

    def project

    @BeforeTest
    void setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'org.m2ci.msp.speech-corpus'
    }

    @Test
    void canApplyPlugin() {
        assert project.pluginManager.hasPlugin('org.m2ci.msp.speech-corpus')
    }
}

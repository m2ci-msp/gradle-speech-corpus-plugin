package org.m2ci.msp.speechcorpus

import org.testng.annotations.*

import org.gradle.testkit.runner.GradleRunner

import static org.gradle.testkit.runner.TaskOutcome.*

class SpeechCorpusPluginFunctionalTest {

    def projectDir
    def gradle

    @BeforeMethod
    void setUp() {
        projectDir = File.createTempDir()
        def buildScript = new File(projectDir, 'build.gradle')
        buildScript.text = """|plugins {
                              |  id 'org.m2ci.msp.speech-corpus'
                              |}
                              |""".stripMargin()
        gradle = GradleRunner.create().withPluginClasspath().withProjectDir(projectDir)
        new File(projectDir, "${projectDir.name}.yaml").withOutputStream { stream ->
            stream << getClass().getResourceAsStream('foobarbaz.yaml')
        }
    }

    @Test
    void testHelp() {
        def result = gradle.build()
        assert result.task(':help').outcome == SUCCESS
    }

    @Test
    void testExtractLab() {
        def result = gradle.withArguments(':extractLab').build()
        assert result.task(':extractLab').outcome == SUCCESS
        result = gradle.withArguments(':extractLab').build()
        assert result.task(':extractLab').outcome == UP_TO_DATE
    }
}

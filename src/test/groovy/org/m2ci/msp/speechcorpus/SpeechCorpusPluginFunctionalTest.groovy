package org.m2ci.msp.speechcorpus

import org.testng.annotations.*

import org.gradle.testkit.runner.GradleRunner

import static org.gradle.testkit.runner.TaskOutcome.*

class SpeechCorpusPluginFunctionalTest {

    def projectDir
    def buildScript
    def gradle
    def yamlFileName = 'foobarbaz.yaml'
    def flacFileName = 'foobarbaz.flac'

    @BeforeMethod
    void setUp() {
        projectDir = File.createTempDir()
        buildScript = new File(projectDir, 'build.gradle')
        buildScript.text = """|plugins {
                              |  id 'org.m2ci.msp.speech-corpus'
                              |}
                              |
                              |speechCorpus {
                              |  yamlFile = '$yamlFileName'
                              |  flacFile = '$flacFileName'
                              |}
                              |""".stripMargin()
        gradle = GradleRunner.create().withPluginClasspath().withProjectDir(projectDir)
        [yamlFileName, flacFileName].each { resource ->
            new File(projectDir, resource).withOutputStream { stream ->
                stream << getClass().getResourceAsStream(resource)
            }
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
        ['foo.lab', 'bar.lab', 'baz.lab'].each { labFile ->
            def actual = new File("$projectDir/build/lab/$labFile").text
            def expected = getClass().getResourceAsStream(labFile).text
            assert LabFile.parse(actual) == LabFile.parse(expected)
        }
    }

    @Test
    void testExtractText() {
        def result = gradle.withArguments(':extractText').build()
        assert result.task(':extractText').outcome == SUCCESS
        result = gradle.withArguments(':extractText').build()
        assert result.task(':extractText').outcome == UP_TO_DATE
        ['foo.txt', 'bar.txt', 'baz.txt'].each { txtFile ->
            def actual = new File("$projectDir/build/text/$txtFile").text
            def expected = getClass().getResourceAsStream(txtFile).text
            assert actual == expected
        }
    }

    @Test
    void testExtractTextGrid() {
        def result = gradle.withArguments(':extractTextGrid').build()
        assert result.task(':extractTextGrid').outcome == SUCCESS
        result = gradle.withArguments(':extractTextGrid').build()
        assert result.task(':extractTextGrid').outcome == UP_TO_DATE
        def actual = new File("$projectDir/build/${projectDir.name}.TextGrid").text
        def expected = getClass().getResourceAsStream('foobarbaz.TextGrid').text
        assert actual == expected: projectDir
    }

    @Test
    void testExtractWav() {
        def result = gradle.withArguments(':extractWav').build()
        assert result.task(':extractWav').outcome == SUCCESS
        result = gradle.withArguments(':extractWav').build()
        assert result.task(':extractWav').outcome == UP_TO_DATE
        ['foo.wav', 'bar.wav', 'baz.wav'].each { wavFile ->
            def actual = new File("$projectDir/build/wav/$wavFile").text
            def expected = getClass().getResourceAsStream(wavFile).text
            assert actual == expected
        }
    }

    @Test
    void testConcatenateToFlac() {
        def wavDir = new File("$projectDir/wav")
        wavDir.mkdirs()
        ['foo.wav', 'bar.wav', 'baz.wav'].each { wavFile ->
            new File(wavDir, wavFile).withOutputStream { stream ->
                stream << getClass().getResourceAsStream(wavFile)
            }
        }
        def flacFile = new File(projectDir, 'actual.flac')
        buildScript << """|
                          |task concatenateToFlac(type: org.m2ci.msp.speechcorpus.tasks.ConcatFlac) {
                          |  srcDir = 'wav'
                          |  destFile = '$flacFile.name'
                          |}
                          |""".stripMargin()
        def result = gradle.withArguments(':concatenateToFlac').build()
        assert result.task(':concatenateToFlac').outcome == SUCCESS
        result = gradle.withArguments(':concatenateToFlac').build()
        assert result.task(':concatenateToFlac').outcome == UP_TO_DATE
    }
}

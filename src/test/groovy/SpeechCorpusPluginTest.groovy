import org.testng.annotations.Test

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class SpeechCorpusPluginTest {

    @Test
    public void canApplyPlugin() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'org.m2ci.msp.speech-corpus'
        assert project.pluginManager.hasPlugin('org.m2ci.msp.speech-corpus')
    }
}

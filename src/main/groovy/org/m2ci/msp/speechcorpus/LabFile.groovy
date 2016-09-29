package org.m2ci.msp.speechcorpus

class LabFile {

    def endTimes
    def labels

    LabFile() {
        endTimes = []
        labels = []
    }

    boolean equals(LabFile other) {
        this.endTimes == other.endTimes && this.labels == other.labels
    }

    static LabFile parse(String text) {
        def labFile = new LabFile()
        def header = true
        text.eachLine { line ->
            switch (line.trim()) {
                case '#':
                    header = false
                    break
                default:
                    if (!header) {
                        def fields = line.trim().split('\\s+', 3)
                        def endTime = fields[0] as float
                        def label = fields.size() > 2 ? fields[2] : ''
                        labFile.endTimes << endTime
                        labFile.labels << label
                    }
                    break
            }
        }
        labFile
    }
}

package amelinium1.grails

import org.hibernate.SessionFactory;

class ProjectService {

    def createProject(String projectName) {

        def comment = new Comment(name:"",text:"")
        if (!comment.save(flush: true)) {
            comment.errors.each { println it // Poprawic zeby wyswietlalo na stronie ze sie nie udalo
            }
        }

        def backlog = new Backlog(ver:1,text:"",comment:comment)
        if (!backlog.save(flush: true)) {
            backlog.errors.each { println it }
        }

        def commentCsv = new Comment(name:"",text:"")
        if (!commentCsv.save(flush: true)) {
            commentCsv.errors.each { println it }
        }

        def csv = new Csv(ver:1,text:"",comment:comment)
        if (!csv.save(flush: true)) {
            csv.errors.each { println it }
        }

        def project=new Project(ver:1, name:projectName, status:"Open")
        if (!project.save(flush: true)) {
            project.errors.each { println it }
        }

        def revision = new Revision(ver:1,backlog:backlog,csv:csv)
        if (!revision.save(flush: true)) {
            revision.errors.each { println it }
        }

        project.revision = revision
        project.addToRevisions(revision)

        if (!project.save(flush: true)) {
            project.errors.each { println it }
        }
        project
    }

    def updateBacklog(Project project, String backlogText, String commentText) {

        def comment = new Comment(name:"",text:commentText)
        if (!comment.save(flush: true)) {
            comment.errors.each { println it }
        }

        def backlogVer = project.revision.backlog.ver + 1
        def backlog = new Backlog(ver:backlogVer,text:backlogText,comment:comment)
        if (!backlog.save(flush: true)) {
            backlog.errors.each { println it }
        }

        def csv = project.revision.csv

        def revisionVer = project.revision.ver + 1
        def revision = new Revision(ver:revisionVer,backlog:backlog,csv:csv,project:project)
        if (!revision.save(flush: true)) {
            revision.errors.each { println it }
        }

        project.revision = revision
        project.addToRevisions(revision)
        if (!project.save(flush:true)) {
            project.errors.each { println it }
        }
        project
    }

    def updateCsv(Project project, String csvText, String commentText) {

        def comment = new Comment(name:"",text:commentText)
        if (!comment.save(flush: true)) {
            comment.errors.each { println it // Poprawic zeby wyswietlalo na stronie ze sie nie udalo
            }
        }

        def csvVer = project.revision.csv.ver + 1
        def csv = new Csv(ver:csvVer, text:csvText, comment:comment)
        if (!csv.save(flush: true)) {
            csv.errors.each { println it }
        }

        def backlog = project.revision.backlog

        def revisionVer = project.revision.ver + 1
        def revision = new Revision(ver:revisionVer, backlog:backlog, csv:csv, project:project)
        if (!revision.save(flush: true)) {
            revision.errors.each { println it }
        }

        project.revision = revision
        project.addToRevisions(revision)
        if (!project.save(flush: true)) {
            project.errors.each { println it }
        }
        project
    }

    def updateBacklogCsv(Project project, String backlogText, String csvText) {

        def backlogVer = project.revision.backlog.ver + 1
        def backlog = new Backlog(ver:backlogVer,text:backlogText)
        if (!backlog.save(flush: true)) {
            backlog.errors.each { println it }
        }

        def csvVer = project.revision.csv.ver + 1
        def csv = new Csv(ver:csvVer,text:csvText)
        if (!csv.save(flush: true)) {
            csv.errors.each { println it }
        }

        def revisionVer = project.revision.ver + 1
        def revision = new Revision(ver:revisionVer,backlog:backlog,csv:csv, project:project)
        if (!revision.save(flush: true)) {
            revision.errors.each { println it }
        }

        project.revision = revision
        project.addToRevisions(revision)
        if (!project.save(flush: true)) {
            project.errors.each { println it }
        }
        project
    }
}

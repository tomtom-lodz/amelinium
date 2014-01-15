package amelinium1.grails

import org.hibernate.SessionFactory;

class ProjectService {

    def createProject(String projectName, String userName, Integer sprintLength, Integer velocity, Integer scopeIncrease) {

        def backlog = new Backlog(ver:1,text:"BACKLOG - do not remove this line (needed for automatic recalculation)", state:"Recalculated", editedBy:userName)
        if (!backlog.save(flush: true)) {
            backlog.errors.each { println it }
        }

        def csv = new Csv(ver:1,text:"", editedBy:userName)
        if (!csv.save(flush: true)) {
            csv.errors.each { println it }
        }

        def project=new Project(ver:1, name:projectName, status:"Open")
        if (!project.save(flush: true)) {
            project.errors.each { println it }
        }

        def revision = new Revision(ver:1,backlog:backlog,csv:csv,comment:"Project creation",changedBy:userName)
        if (!revision.save(flush: true)) {
            revision.errors.each { println it }
        }

        project.revision = revision
        project.addToRevisions(revision)
        project.createdBy = userName
        project.sprintLength = sprintLength
        project.velocity = velocity
        project.scopeIncrease = scopeIncrease

        if (!project.save(flush: true)) {
            project.errors.each { println it }
        }
        project
    }

    def updateBacklog(Project project, String backlogText, String commentText, String editedBy) {

        def backlogVer = project.revision.backlog.ver + 1
        def backlog = new Backlog(ver:backlogVer,text:backlogText, state:"Not recalculated", editedBy:editedBy)
        if (!backlog.save(flush: true)) {
            backlog.errors.each { println it }
        }

        def csv = project.revision.csv

        def revisionVer = project.revision.ver + 1
        def revision = new Revision(ver:revisionVer,backlog:backlog,csv:csv,project:project,comment:commentText,changedBy:editedBy)
        if (!revision.save(flush: true)) {
            revision.errors.each { println it }
        }

        project.revision = revision
        project.editedBy = editedBy
        project.addToRevisions(revision)
        if (!project.save(flush:true)) {
            project.errors.each { println it }
        }
        project
    }

    def updateCsv(Project project, String csvText, String commentText, String editedBy) {

        def csvVer = project.revision.csv.ver + 1
        def csv = new Csv(ver:csvVer, text:csvText, editedBy:editedBy)
        if (!csv.save(flush: true)) {
            csv.errors.each { println it }
        }

        def backlog = project.revision.backlog

        def revisionVer = project.revision.ver + 1
        def revision = new Revision(ver:revisionVer, backlog:backlog, csv:csv, project:project,comment:commentText,changedBy:editedBy)
        if (!revision.save(flush: true)) {
            revision.errors.each { println it }
        }

        project.revision = revision
        project.editedBy = editedBy
        project.addToRevisions(revision)
        if (!project.save(flush: true)) {
            project.errors.each { println it }
        }
        project
    }

/*    def updateBacklogCsv(Project project, String backlogText, String csvText) {

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
    }*/
}

package amelinium1.grails



import org.junit.*
import grails.test.mixin.*

@TestFor(BacklogController)
@Mock(Backlog)
class BacklogControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/backlog/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.backlogInstanceList.size() == 0
        assert model.backlogInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.backlogInstance != null
    }

    void testSave() {
        controller.save()

        assert model.backlogInstance != null
        assert view == '/backlog/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/backlog/show/1'
        assert controller.flash.message != null
        assert Backlog.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/backlog/list'

        populateValidParams(params)
        def backlog = new Backlog(params)

        assert backlog.save() != null

        params.id = backlog.id

        def model = controller.show()

        assert model.backlogInstance == backlog
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/backlog/list'

        populateValidParams(params)
        def backlog = new Backlog(params)

        assert backlog.save() != null

        params.id = backlog.id

        def model = controller.edit()

        assert model.backlogInstance == backlog
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/backlog/list'

        response.reset()

        populateValidParams(params)
        def backlog = new Backlog(params)

        assert backlog.save() != null

        // test invalid parameters in update
        params.id = backlog.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/backlog/edit"
        assert model.backlogInstance != null

        backlog.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/backlog/show/$backlog.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        backlog.clearErrors()

        populateValidParams(params)
        params.id = backlog.id
        params.version = -1
        controller.update()

        assert view == "/backlog/edit"
        assert model.backlogInstance != null
        assert model.backlogInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/backlog/list'

        response.reset()

        populateValidParams(params)
        def backlog = new Backlog(params)

        assert backlog.save() != null
        assert Backlog.count() == 1

        params.id = backlog.id

        controller.delete()

        assert Backlog.count() == 0
        assert Backlog.get(backlog.id) == null
        assert response.redirectedUrl == '/backlog/list'
    }
}

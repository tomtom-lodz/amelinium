package amelinium1.grails



import org.junit.*
import grails.test.mixin.*

@TestFor(RevisionController)
@Mock(Revision)
class RevisionControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/revision/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.revisionInstanceList.size() == 0
        assert model.revisionInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.revisionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.revisionInstance != null
        assert view == '/revision/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/revision/show/1'
        assert controller.flash.message != null
        assert Revision.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/revision/list'

        populateValidParams(params)
        def revision = new Revision(params)

        assert revision.save() != null

        params.id = revision.id

        def model = controller.show()

        assert model.revisionInstance == revision
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/revision/list'

        populateValidParams(params)
        def revision = new Revision(params)

        assert revision.save() != null

        params.id = revision.id

        def model = controller.edit()

        assert model.revisionInstance == revision
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/revision/list'

        response.reset()

        populateValidParams(params)
        def revision = new Revision(params)

        assert revision.save() != null

        // test invalid parameters in update
        params.id = revision.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/revision/edit"
        assert model.revisionInstance != null

        revision.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/revision/show/$revision.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        revision.clearErrors()

        populateValidParams(params)
        params.id = revision.id
        params.version = -1
        controller.update()

        assert view == "/revision/edit"
        assert model.revisionInstance != null
        assert model.revisionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/revision/list'

        response.reset()

        populateValidParams(params)
        def revision = new Revision(params)

        assert revision.save() != null
        assert Revision.count() == 1

        params.id = revision.id

        controller.delete()

        assert Revision.count() == 0
        assert Revision.get(revision.id) == null
        assert response.redirectedUrl == '/revision/list'
    }
}

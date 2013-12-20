package amelinium1.grails



import org.junit.*
import grails.test.mixin.*

@TestFor(CsvController)
@Mock(Csv)
class CsvControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/csv/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.csvInstanceList.size() == 0
        assert model.csvInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.csvInstance != null
    }

    void testSave() {
        controller.save()

        assert model.csvInstance != null
        assert view == '/csv/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/csv/show/1'
        assert controller.flash.message != null
        assert Csv.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/csv/list'

        populateValidParams(params)
        def csv = new Csv(params)

        assert csv.save() != null

        params.id = csv.id

        def model = controller.show()

        assert model.csvInstance == csv
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/csv/list'

        populateValidParams(params)
        def csv = new Csv(params)

        assert csv.save() != null

        params.id = csv.id

        def model = controller.edit()

        assert model.csvInstance == csv
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/csv/list'

        response.reset()

        populateValidParams(params)
        def csv = new Csv(params)

        assert csv.save() != null

        // test invalid parameters in update
        params.id = csv.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/csv/edit"
        assert model.csvInstance != null

        csv.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/csv/show/$csv.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        csv.clearErrors()

        populateValidParams(params)
        params.id = csv.id
        params.version = -1
        controller.update()

        assert view == "/csv/edit"
        assert model.csvInstance != null
        assert model.csvInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/csv/list'

        response.reset()

        populateValidParams(params)
        def csv = new Csv(params)

        assert csv.save() != null
        assert Csv.count() == 1

        params.id = csv.id

        controller.delete()

        assert Csv.count() == 0
        assert Csv.get(csv.id) == null
        assert response.redirectedUrl == '/csv/list'
    }
}

<template>
  <div class="rent-package-wrapper">
    <h1>Rent movies</h1>
    <div id="rent-package-buttons">
      <button @click="addRentalPackage()" id="rent-package-submit-button">Submit</button>
      <button @click="addRentalElement()" id="rent-package-add-button">Add record</button>
    </div>
    <vue-virtual-table
      class="v-table"
      :config="tableConfig"
      :data="tableData"
      :minWidth="800"
      :height=$tableHeight
      :itemHeight="45"
      :bordered="true"
    >
      <template slot-scope="scope" slot="actionCommon">
        <button @click="editRecord(scope.row)">Edit</button>
        <button @click="deleteRecord(scope.row)">Delete</button>
      </template>
    </vue-virtual-table>

    <form-modal name="editModal" :width="600" :height="610">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID Rental:<br/>
        <input type="text" v-model="newRecordData.idRental" disabled><br/>
        ID Movie in warehouse:<br/>
        <input type="text" v-model="newRecordData.idMovieWarehouse" disabled><br/>
        ID Client:<br/>
        <input type="text" v-model="newRecordData.idClient" disabled><br/>
        Client name:<br/>
        <input type="text" v-model="newRecordData.clientName" disabled><br/>
        ID Rental office:<br/>
        <input type="text" v-model="newRecordData.idRentalOffice" disabled><br/>
        Rental office name:<br/>
        <input type="text" v-model="newRecordData.rentalOfficeName" disabled><br/>
        ID Movie:<br/>
        <input type="text" v-model="newRecordData.idMovie" disabled><br/>
        Movie title:<br/>
        <input type="text" v-model="newRecordData.title" disabled><br/>
        Amount taken:<br/>
        <input type="text" v-model="newRecordData.amountOfRentals"><br/>
        <button @click="editRecordSubmit" style="float:right;">Submit</button><button @click="$modal.hide('editModal')" >Cancel</button>
      </div>
    </form-modal>

    <form-modal name="selectOfficeModal" :width="600" :height="550">
      <div class="modal-box select-essentials-modal">
        <span class="modal-box-title">Specify rental office:</span>
        Name:<br/>
        <cool-select
          class="cool-select-input"
          v-model="RentalEssentials.idRentalOffice"
          :items="selectSuggestionsData.rentalOffices"
          item-text="rentalOfficeNameID"
          item-value="idRentalOffice"/><br/>
        <button @click="$modal.hide('selectOfficeModal')">Cancel</button>
        <button @click="goToSelectEmployeeClientModal()" style="float:right;">Next</button>
      </div>
    </form-modal>

    <form-modal name="selectEmployeeClientModal" :width="600" :height="610">
      <div class="modal-box select-essentials-modal">
        <span class="modal-box-title">Specify employee and client:</span>
        Employee:<br/>
        <cool-select
          class="cool-select-input"
          v-model="RentalEssentials.idEmployee"
          :items="selectSuggestionsData.employees"
          item-text="employeeNameID"
          item-value="idEmployee"/><br/>
        Client:<br/>
        <cool-select
          class="cool-select-input"
          v-model="RentalEssentials.idClient"
          :items="selectSuggestionsData.clients"
          item-text="clientNameAddress"
          item-value="idClient"/><br/>
        <button @click="$modal.hide('selectEmployeeClientModal')">Cancel</button>
        <button @click="goToAddRecordModal()" style="float:right;">Next</button>
      </div>
    </form-modal>

    <form-modal name="selectRentalElementModal" :width="600" :height="610">
      <div class="modal-box select-essentials-modal">
        <span class="modal-box-title">Specify rental element:</span>
        Movie:<br/>
        <cool-select
          class="cool-select-input"
          v-model="RentalElementObject.idMovieWarehouse"
          :items="selectSuggestionsData.movies"
          item-text="titleID"
          item-value="idMovie"/><br/>

        <button @click="$modal.hide('selectRentalElementModal')">Cancel</button>
        <button @click="goToAddRecordModal()" style="float:right;">Next</button>
      </div>
    </form-modal>

    <form-modal name="alertModal" @before-open="beforeOpenAlert" :height="$alertHeight" :width="$alertWidth">
      <div class="modal-box alert-modal-box">
        {{alertText}}
        <button @click="$modal.hide('alertModal')">Close</button>
      </div>
    </form-modal>
  </div>
</template>

<script>
  import axios from 'axios'
  import TheDatabaseNavigation from '../../components/TheDatabaseNavigation.vue'
  import VueVirtualTable from 'vue-virtual-table'
  import { CoolSelect } from 'vue-cool-select'

  export default {
    name: 'RentalElement',
    components: {
      TheDatabaseNavigation,
      VueVirtualTable,
      CoolSelect
    },
    data () {
      return {
        tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
          { prop: 'title', name: 'Movie', searchable: true, sortable: true },
          { prop: 'director', name: 'Director', width: 170, filterable: true, sortable: true },
          { prop: 'amountOfRentals', name: 'Amount', width: 80, numberFilter: true, sortable: true },
          { prop: 'clientName', name: 'Client', width: 200},
          { prop: 'employeeName', name: 'Employee', width: 200},
          { prop: 'rentalOfficeName', name: 'Office', width: 130},
          { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
        ],
        tableData: [],
        RentalElementObject: {
          amountOfRentals: '',
          idMovieWarehouse: '',
          idRental: ''
        },
        postObject: {
          rental: {
            rentalDate: '',
            returnDate: '',
            idClient: '',
            idEmployee: ''
          },
          rentalElements: []
        },
        RentalEssentials: {
          idRentalOffice: '',
          idEmployee: '',
          idClient: '',
          rentalOfficeName: '',
          employeeName: '',
          clientName: ''
        },
        selectSuggestionsData: {
          rentalOffices: [],
          warehouseMovies: [],
          clients: [],
          employees: []
        },
        oldRecordData: {},
        newRecordData: {},
        cleanObject: {},
        cleanRentalElementObject: {},
        alertText: '',
        isOfficeSpecified: false,
        isEmployeeClientSpecified: false,
      }
    },
    created () {
      this.cleanAddRentalElementObject = _.cloneDeep(this.RentalElementObject)
      axios.get('/rentalOffice/all')
        .then(response => {
          function responseConstructor (idRentalOffice, rentalOfficeName) {
            this.idRentalOffice = idRentalOffice
            this.rentalOfficeNameID = rentalOfficeName + ' ID: ' + idRentalOffice
            this.rentalOfficeName = rentalOfficeName
          }
          let i
          for (i = 0; i < response.data.length; i++) {
            this.selectSuggestionsData.rentalOffices.push(new responseConstructor(response.data[i].idRentalOffice, response.data[i].rentalOfficeName))
          }
        })
    },
    methods: {
      addRentalElement () {
        if(!this.isOfficeSpecified) {
          this.$modal.show('selectOfficeModal')
        }
        else if(!this.isEmployeeClientSpecified) {
          this.$modal.show('selectEmployeeClientModal')
        }
        else {
          this.$modal.show('selectRentalElementModal')
        }
      },


      goToSelectEmployeeClientModal () {
        axios.get('/rentalOffice/' + this.RentalEssentials.idRentalOffice + '/clients')
          .then(response => {
            function responseConstructor(idClient, clientFirstName, clientLastName, street, houseNumber){
              this.idClient = idClient
              this.clientNameAddress = clientFirstName + ' ' + clientLastName + ', ' + street + ' ' + houseNumber
              this.clientName = clientFirstName + ' ' + clientLastName
            }
            let i
            for(i=0; i<response.data.length; i++) {
              this.selectSuggestionsData.clients.push(new responseConstructor(response.data[i].idClient, response.data[i].clientFirstName, response.data[i].clientLastName,
                response.data[i].adress.street, response.data[i].adress.houseNumber))
            }
          })
        axios.get('/rentalOffice/' + this.RentalEssentials.idRentalOffice + '/employees')
          .then(response => {
            function responseConstructor(idEmployee, firstName, lastName){
              this.idEmployee = idEmployee
              this.employeeNameID = firstName + ' ' + lastName + ' ID: ' + idEmployee
              this.employeeName = firstName + ' ' + lastName
            }
            let i
            for(i=0; i<response.data.length; i++) {
              this.selectSuggestionsData.employees.push(new responseConstructor(response.data[i].idEmployee, response.data[i].firstName, response.data[i].lastName))
            }
          })
        this.isOfficeSpecified = true
        this.$modal.show('selectEmployeeClientModal')
        this.$modal.hide('selectOfficeModal')
        let specifiedOfficeIndex = this.selectSuggestionsData.rentalOffices.findIndex(obj =>
          obj.idRentalOffice == this.RentalEssentials.idRentalOffice)
        this.RentalEssentials.rentalOfficeName = this.selectSuggestionsData.rentalOffices[specifiedOfficeIndex].rentalOfficeName
      },


      goToAddRecordModal () {
        axios.get('/rentalOffice/' + this.RentalEssentials.idRentalOffice + '/warehouse/all')
          .then(response => {
            function responseConstructor(idMovieWarehouse, idMovie, title, quantity, charge, idRentalOffice, rentalOfficeName){
              this.idMovieWarehouse = idMovieWarehouse
              this.title = title
              this.quantity = quantity
              this.charge = charge
              this.idRentalOffice = idRentalOffice
              this.rentalOfficeName = rentalOfficeName
            }
            let i
            for(i=0; i<response.data.length; i++) {
              this.tableData.push(new responseConstructor(response.data[i].idMovieWarehouse, response.data[i].movie.idMovie,
                response.data[i].movie.title, response.data[i].quantity, response.data[i].charge,
                response.data[i].rentalOffice.idRentalOffice, response.data[i].rentalOffice.rentalOfficeName,))
            }
          })
        this.isEmployeeClientSpecified = true
        this.$modal.show('selectRentalElementModal')
        this.$modal.hide('selectEmployeeClientModal')
        let specifiedEmployeeIndex = this.selectSuggestionsData.employees.findIndex(obj =>
          obj.idEmployee == this.RentalEssentials.idEmployee)
        this.RentalEssentials.employeeName = this.selectSuggestionsData.employees[specifiedEmployeeIndex].employeeName
        let specifiedClientIndex = this.selectSuggestionsData.clients.findIndex(obj =>
          obj.idClient == this.RentalEssentials.idClient)
        this.RentalEssentials.clientName = this.selectSuggestionsData.clients[specifiedClientIndex].clientName
      },


      editRecord: function (recordData) {
        this.$modal.show('editModal')
        this.oldRecordData = recordData
        this.newRecordData = recordData
      },


      editRecordSubmit: function () {
        axios.put('/rentalElement/' + this.oldRecordData.idMovieWarehouse + '/' + this.oldRecordData.idRental, this.newRecordData)
          .then(response => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            this.$modal.hide('editModal')
            let editElemIndex = this.tableData.findIndex(tableElem =>
              (tableElem.idRental == this.oldRecordData.idRental && tableElem.idMovieWarehouse == this.oldRecordData.idMovieWarehouse))
            this.$set(this.tableData, editElemIndex, this.newRecordData)
          })
          .catch(error => {
            if (error.response) {
              this.$modal.show('alertModal',
                { text: 'Operation failed.  |  ' + error.response.status + '  |  ' + error.response.data.error + '  |  ' + error.response.data.message })
            }
          })
      },


      deleteRecord: function (recordData) {
        axios.delete('/rentalElement/' + recordData.idMovieWarehouse + '/' + recordData.idRental)
          .then(() => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            let deleteElemIndex = this.tableData.findIndex(tableElem =>
              (tableElem.idMovieWarehouse == recordData.idMovieWarehouse && tableElem.idRental == recordData.idRental))
            this.tableData.splice(deleteElemIndex, 1)
          })
          .catch(error => {
            if (error.response) {
              this.$modal.show('alertModal',
                { text: 'Operation failed.  |  ' + error.response.status + '  |  ' + error.response.data.error + '  |  ' + error.response.data.message })
            }
          })
      },


      beforeOpenAlert (event) {
        this.alertText = event.params.text
      }
    }
  }
</script>
<style scoped>

</style>

import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import PersonelTanimUpdatePage from './personel-tanim-update.page-object';

const expect = chai.expect;
export class PersonelTanimDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('notlarimApp.personelTanim.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-personelTanim'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class PersonelTanimComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('personel-tanim-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('personel-tanim');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreatePersonelTanim() {
    await this.createButton.click();
    return new PersonelTanimUpdatePage();
  }

  async deletePersonelTanim() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const personelTanimDeleteDialog = new PersonelTanimDeleteDialog();
    await waitUntilDisplayed(personelTanimDeleteDialog.deleteModal);
    expect(await personelTanimDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/notlarimApp.personelTanim.delete.question/);
    await personelTanimDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(personelTanimDeleteDialog.deleteModal);

    expect(await isVisible(personelTanimDeleteDialog.deleteModal)).to.be.false;
  }
}

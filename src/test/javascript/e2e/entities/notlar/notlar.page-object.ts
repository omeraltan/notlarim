import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import NotlarUpdatePage from './notlar-update.page-object';

const expect = chai.expect;
export class NotlarDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('notlarimApp.notlar.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-notlar'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class NotlarComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('notlar-heading'));
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
    await navBarPage.getEntityPage('notlar');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateNotlar() {
    await this.createButton.click();
    return new NotlarUpdatePage();
  }

  async deleteNotlar() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const notlarDeleteDialog = new NotlarDeleteDialog();
    await waitUntilDisplayed(notlarDeleteDialog.deleteModal);
    expect(await notlarDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/notlarimApp.notlar.delete.question/);
    await notlarDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(notlarDeleteDialog.deleteModal);

    expect(await isVisible(notlarDeleteDialog.deleteModal)).to.be.false;
  }
}

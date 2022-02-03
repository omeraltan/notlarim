import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import RehberUpdatePage from './rehber-update.page-object';

const expect = chai.expect;
export class RehberDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('notlarimApp.rehber.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-rehber'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class RehberComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('rehber-heading'));
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
    await navBarPage.getEntityPage('rehber');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateRehber() {
    await this.createButton.click();
    return new RehberUpdatePage();
  }

  async deleteRehber() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const rehberDeleteDialog = new RehberDeleteDialog();
    await waitUntilDisplayed(rehberDeleteDialog.deleteModal);
    expect(await rehberDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/notlarimApp.rehber.delete.question/);
    await rehberDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(rehberDeleteDialog.deleteModal);

    expect(await isVisible(rehberDeleteDialog.deleteModal)).to.be.false;
  }
}

import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class NotBaslikTanimUpdatePage {
  pageTitle: ElementFinder = element(by.id('notlarimApp.notBaslikTanim.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  baslikInput: ElementFinder = element(by.css('input#not-baslik-tanim-baslik'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setBaslikInput(baslik) {
    await this.baslikInput.sendKeys(baslik);
  }

  async getBaslikInput() {
    return this.baslikInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setBaslikInput('baslik');
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}

import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class NotlarUpdatePage {
  pageTitle: ElementFinder = element(by.id('notlarimApp.notlar.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  notInput: ElementFinder = element(by.css('input#notlar-not'));
  baslikSelect: ElementFinder = element(by.css('select#notlar-baslik'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNotInput(not) {
    await this.notInput.sendKeys(not);
  }

  async getNotInput() {
    return this.notInput.getAttribute('value');
  }

  async baslikSelectLastOption() {
    await this.baslikSelect.all(by.tagName('option')).last().click();
  }

  async baslikSelectOption(option) {
    await this.baslikSelect.sendKeys(option);
  }

  getBaslikSelect() {
    return this.baslikSelect;
  }

  async getBaslikSelectedOption() {
    return this.baslikSelect.element(by.css('option:checked')).getText();
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
    await this.setNotInput('not');
    await this.baslikSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}

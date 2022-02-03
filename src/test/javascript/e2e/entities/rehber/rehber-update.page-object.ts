import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class RehberUpdatePage {
  pageTitle: ElementFinder = element(by.id('notlarimApp.rehber.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  telefonInput: ElementFinder = element(by.css('input#rehber-telefon'));
  adresInput: ElementFinder = element(by.css('input#rehber-adres'));
  personelSelect: ElementFinder = element(by.css('select#rehber-personel'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTelefonInput(telefon) {
    await this.telefonInput.sendKeys(telefon);
  }

  async getTelefonInput() {
    return this.telefonInput.getAttribute('value');
  }

  async setAdresInput(adres) {
    await this.adresInput.sendKeys(adres);
  }

  async getAdresInput() {
    return this.adresInput.getAttribute('value');
  }

  async personelSelectLastOption() {
    await this.personelSelect.all(by.tagName('option')).last().click();
  }

  async personelSelectOption(option) {
    await this.personelSelect.sendKeys(option);
  }

  getPersonelSelect() {
    return this.personelSelect;
  }

  async getPersonelSelectedOption() {
    return this.personelSelect.element(by.css('option:checked')).getText();
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
    await this.setTelefonInput('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setAdresInput('adres');
    await this.personelSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}

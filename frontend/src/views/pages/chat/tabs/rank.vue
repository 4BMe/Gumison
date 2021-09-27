<script>
import simplebar from "simplebar-vue";

import { contacts } from "../data";

export default {
  components: {
    simplebar,
  },
  methods: {},
  data() {
    return {
      contacts: contacts,
      contactsFilter: contacts,
    };
  },
};
</script>

<template>
  <!-- Start Contact content -->
  <div>
    <div class="p-4">
      <div class="user-chat-nav float-right">
        <div v-b-tooltip.hover title="Add Contact">
          <!-- Button trigger modal -->
          <button
            type="button"
            class="btn btn-link text-decoration-none text-muted font-size-18 py-0"
            v-b-modal.modal-1
          >
            <i class="ri-user-add-line"></i>
          </button>

          <!-- Start Add contact Modal -->
          <b-modal
            id="modal-1"
            centered
            :title="$t('chat.tabs.contacts.modal.title')"
            body-class="p-4"
            title-class="font-size-16"
            cancel-title="Close"
            cancel-variant="link"
            ok-title="Invite Contact"
          >
            <form>
              <div class="form-group mb-4">
                <label for="addcontactemail-input">{{
                  $t("chat.tabs.contacts.modal.form.email.label")
                }}</label>
                <input
                  type="email"
                  class="form-control"
                  id="addcontactemail-input"
                  :placeholder="
                    $t('chat.tabs.contacts.modal.form.email.placeholder')
                  "
                />
              </div>
              <div class="form-group">
                <label for="addcontact-invitemessage-input">{{
                  $t("chat.tabs.contacts.modal.form.message.label")
                }}</label>
                <textarea
                  class="form-control"
                  id="addcontact-invitemessage-input"
                  rows="3"
                  :placeholder="
                    $t('chat.tabs.contacts.modal.form.message.placeholder')
                  "
                ></textarea>
              </div>
            </form>
          </b-modal>
        </div>
      </div>
      <h4 class="mb-4">{{ $t("chat.tabs.contacts.title") }}</h4>
      <!-- End Add contact Modal -->

      <div class="search-box chat-search-box">
        <div class="input-group bg-light input-group-lg rounded-lg">
          <div class="input-group-prepend">
            <button
              class="btn btn-link text-decoration-none text-muted pr-1"
              type="button"
            >
              <i class="ri-search-line search-icon font-size-18"></i>
            </button>
          </div>
          <input
            type="text"
            class="form-control bg-light"
            :placeholder="$t('chat.tabs.contacts.search.placeholder')"
          />
        </div>
      </div>
      <!-- End search-box -->
    </div>
    <!-- end p-4 -->

    <!-- Start contact lists -->
    <simplebar class="p-4 chat-message-list chat-group-list" data-simplebar>
      <div v-for="(item, index) in contactsFilter" :key="index">
        <div class="p-3 font-weight-bold text-primary" v-if="item.character">
          {{ $t(item.character) }}
        </div>

        <ul class="list-unstyled contact-list" v-if="item.name">
          <li>
            <div class="media align-items-center">
              <div class="media-body">
                <h5 class="font-size-14 m-0">{{ $t(item.name) }}</h5>
              </div>

              <b-dropdown toggle-class="text-muted p-0" variant="white" right>
                <template v-slot:button-content>
                  <i class="ri-more-2-fill"></i>
                </template>
                <a class="dropdown-item">
                  {{ $t("chat.tabs.contacts.list.dropdown.item1") }}
                  <i class="ri-share-line float-right text-muted"></i>
                </a>
                <a class="dropdown-item">
                  {{ $t("chat.tabs.contacts.list.dropdown.item2") }}
                  <i class="ri-forbid-line float-right text-muted"></i>
                </a>
                <a class="dropdown-item">
                  {{ $t("chat.tabs.contacts.list.dropdown.item3") }}
                  <i class="ri-delete-bin-line float-right text-muted"></i>
                </a>
              </b-dropdown>
            </div>
          </li>
        </ul>
      </div>
      <!-- end contact list -->
    </simplebar>
    <!-- end contact lists -->
  </div>
  <!-- Start Contact content -->
</template>
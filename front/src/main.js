import { createApp } from 'vue'
import Vue from 'vue'
import App from './App.vue'

import newThing from './newComponent.vue'

createApp(App).mount('#app')

new Vue({
    render:h=> h(newThing),
}).$mount('#newSpace')

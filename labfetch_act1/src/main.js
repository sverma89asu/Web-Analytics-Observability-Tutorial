import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import posthogPlugin from '../plugins/posthog';

createApp(App)
    .use(posthogPlugin)
    .mount('#app');

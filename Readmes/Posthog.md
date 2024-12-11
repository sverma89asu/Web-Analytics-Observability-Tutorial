![enter image description here](https://posthog.com/brand/posthog-logomark@2x.png)

## Posthog

PostHog is an open-source product analytics platform designed to help businesses understand user behavior on their websites or applications.

## Why use Posthog?

1. Event Tracking
2. Session Recordings
3. Feature Flags
4. Heatmaps
5. User Cohorts
6. Self-Hosted

## Integrating Posthog with Vue.js Application

### Tools needed

1. Node 18 (or later). Click [here](https://nodejs.org/en/download/package-manager).
2. A working Vue.js application. (For this tutorial, I am using our Lab-4 assignment.)
3. An account on Posthog.

To create an account on Posthog. Follow the following steps:

1. Log on to posthog.com and click on **Get Started - Free**![enter image description here](https://github.com/user-attachments/assets/2f2392f0-3c46-45b4-8ee7-bc3bfc1bfa3a)
2. The Sign Up page will look this
   ![enter image description here](https://github.com/user-attachments/assets/dfe2ab9b-d385-4b0b-bbea-33e7f1fdaa3b)
   After filling up the details, click on **Continue**. You will then be redirected to a page looking like this
   ![enter image description here](https://github.com/user-attachments/assets/76f40bc8-b5be-4a25-9524-9fe4ecaeca5a)
   Add details to the fields and click on **Create account**. This will redirect you to verify your email address.

   Upon successful verification, you will see a web-page like this:
   ![enter image description here](https://github.com/user-attachments/assets/b8e89576-aacf-49fc-90cc-c56153e24f4e)
   For now, we will click on **Web Analytics** and click on **Get Started**.

3. We will be greeted on a page like this: ![enter image description here](https://github.com/user-attachments/assets/47f8d07a-ade1-4886-8f51-dc8588dccb1e)
   For now, we will click on **Skip Installation** and move on.

4. Now we will be redirected to Configuration page. We will keep everything as it is and click on **Next**.
   ![enter image description here](https://github.com/user-attachments/assets/e2f7b3cf-c720-4897-85d6-acf3b187cea3)
5. Finally, we will be asked if we want to invite any teammates. We will leave it as it is and click on **Finish**.![enter image description here](https://github.com/user-attachments/assets/1bf8dbdd-5edf-4be9-8291-64489c216803)

We are all SET!! We will be greeted with this screen:
![enter image description here](https://github.com/user-attachments/assets/5ada694d-2f6f-4313-8928-48dcb6e28e87)

### Steps for Integration

1. Open the base directory of your Vue.js application in your IDE.
   ![enter image description here](https://github.com/user-attachments/assets/a741dae8-d47f-4837-9cd2-9e1919b603a1)

2. First install `posthog-js`

   ```ssh
   npm install posthog-js
   ```

3. Now, we need to install a plugin by creating a new folder in your base directory called `plugins` and then a new file `posthog.js`:

   ```ssh
   mkdir plugins
   cd plugins
   touch posthog.js
   ```

   Add the following code to your `posthog.js` file:

   ```ssh
   import posthog from "posthog-js";

   export default {
     install(app) {
       app.config.globalProperties.$posthog = posthog.init(
         "<ph_project_api_key>",
         {
           api_host: "https://us.i.posthog.com",
           person_profiles: 'identified_only',
         }
       );
     },
   };
   ```

   Replace `<ph_project_api_key>` with your your PostHog API key. You can find these in your [project settings](https://app.posthog.com/settings/project).
   ![enter image description here](https://github.com/user-attachments/assets/d211181a-130f-4dab-bdd9-1f1c951c51af)
   You will find your API Key in the **Web Snippet** section.

4. Finally, activate your plugin in `main.js`:

   ```ssh
    import { createApp } from 'vue'
   import App from './App.vue'
   import posthogPlugin from '../plugins/posthog';

   createApp(App)
   .use(posthogPlugin)
   .mount('#app');
   ```

5. Once you’ve done this, reload your app and click the buttons a few times. You should see events appearing in the [PostHog events explorer](https://app.posthog.com/events).![enter image description here](https://github.com/user-attachments/assets/59a7aa46-6bd3-48bf-9f43-52b7f4314b0f)

### NOTE

In case the interactions are not reflected in the [events explorer page](https://app.posthog.com/events), follow the following steps:

1. Open Chrome or Firefox, and search `Privacy Badger Extension`.
   ![enter image description here](https://github.com/user-attachments/assets/85930488-38f6-4429-b09e-ab07d71ef145)

2. Click on the first link and add the extension to your browser.
   ![enter image description here](https://github.com/user-attachments/assets/74cd3e0b-3c05-4d52-8c85-ee678ad4a181)

3. Now open the Vue app in the browser and click on a couple of buttons. Then click on the extensions button and find the Privacy Badger extension there.
   ![enter image description here](https://github.com/user-attachments/assets/62c38f57-c36c-448e-99c2-8502c2dc0241)
   Enable the trackers associated with posthog.

Now the interactions of your applications will be visible.

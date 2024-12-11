import posthog from "posthog-js";

export default {
    install(app) {
        app.config.globalProperties.$posthog = posthog.init(
            "phc_L6rDGwUJN5egkf3qvKPgJox9j1PwgrSWEExsi3VUDb1",
            {
                api_host: "https://us.i.posthog.com",
                person_profiles: 'identified_only',
            }
        );
    },
};
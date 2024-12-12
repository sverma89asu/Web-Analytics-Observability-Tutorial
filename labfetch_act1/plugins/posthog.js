import posthog from "posthog-js";

export default {
    install(app) {
        app.config.globalProperties.$posthog = posthog.init(
            "phc_L6aaaaaaaaaaaaaaAAASSAFASDdakcksds",
            {
                api_host: "https://us.i.posthog.com",
                person_profiles: 'identified_only',
            }
        );
    },
};
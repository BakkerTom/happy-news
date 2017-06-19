let config = null;

if (process.env.NODE_ENV === 'development') {
  config = require('../config/config.dev.json');
} else {
  config = require('../config/config.json');
}

/**
 * Configuration file.
 */
class Config {

  /**
   * Get a config key.
   *
   * @param key The key.
   * @returns {*}
   * @throws {Error} If the config key is not set.
   */
  static get(key) {
    if (this.has(key)) {
      return config[key];
    } else {
      throw Error(`Config key '${key}' not set.`);
    }
  }

  /**
   * Check if the config key exists.
   *
   * @param key The key.
   * @returns {boolean} True if the key exists.
   */
  static has(key) {
    return config.hasOwnProperty(key);
  }
}

export default Config;

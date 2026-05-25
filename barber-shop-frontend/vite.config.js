import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';
import { resolve } from 'path';

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '');
  const isLib = mode === 'lib';

  const shared = {
    plugins: [react()],
    define: {
      'import.meta.env.VITE_API_BASE_URL': JSON.stringify(
        env.VITE_API_BASE_URL || 'http://localhost:8080'
      ),
    },
    server: {
      port: 3000,
      open: true,
    },
  };

  if (isLib) {
    return {
      ...shared,
      build: {
        lib: {
          entry: resolve(__dirname, 'src/index.js'),
          name: 'BarberShopUI',
          fileName: 'barber-shop-ui',
          formats: ['es', 'cjs'],
        },
        rollupOptions: {
          external: ['react', 'react-dom', 'react-router-dom'],
          output: {
            globals: {
              react: 'React',
              'react-dom': 'ReactDOM',
              'react-router-dom': 'ReactRouterDOM',
            },
          },
        },
        cssCodeSplit: false,
      },
    };
  }

  return {
    ...shared,
    build: {
      outDir: 'dist-app',
    },
  };
});

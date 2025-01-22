/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: ['class'], // Dark mode support via class
  content: [
    './pages/**/*.{js,ts,jsx,tsx,mdx}', // Page files
    './components/**/*.{js,ts,jsx,tsx,mdx}', // Components folder
    './app/**/*.{js,ts,jsx,tsx,mdx}', // App folder
    './src/**/*.{js,ts,jsx,tsx}', // Your src folder if you have it
    './node_modules/shadcn-ui/**/*.{js,ts,jsx,tsx}', // ShadCN UI components
  ],
  theme: {
    extend:{
        backgroundImage: {
              'diagonal-split': 'linear-gradient(to bottom right , #1e293b  50%,   #000505 50%)',
              'diagonal-split-dark': 'linear-gradient(to top right, #1e293b #ff7eb3 50%, #7ed8ff 50%)',},


      colors: {
        background: 'hsl(var(--background))',
        foreground: 'hsl(var(--foreground))',
        brand: {
          dark: '#7D9D9C',
          mid: '#C6D9D8',
          light: '#DCE4C9',
          bright: '#FAF7F0',
        },
        card: {
          DEFAULT: 'hsl(var(--card))',
          foreground: 'hsl(var(--card-foreground))',
        },
        popover: {
          DEFAULT: 'hsl(var(--popover))',
          foreground: 'hsl(var(--popover-foreground))',
        },
        primary: {
          DEFAULT: 'hsl(var(--primary))',
          foreground: 'hsl(var(--primary-foreground))',
        },
        secondary: {
          DEFAULT: 'hsl(var(--secondary))',
          foreground: 'hsl(var(--secondary-foreground))',
        },
        muted: {
          DEFAULT: 'hsl(var(--muted))',
          foreground: 'hsl(var(--muted-foreground))',
        },
        accent: {
          DEFAULT: 'hsl(var(--accent))',
          foreground: 'hsl(var(--accent-foreground))',
        },
        destructive: {
          DEFAULT: 'hsl(var(--destructive))',
          foreground: 'hsl(var(--destructive-foreground))',
        },
        border: 'hsl(var(--border))',
        input: 'hsl(var(--input))',
        ring: 'hsl(var(--ring))',
        chart: {
          '1': 'hsl(var(--chart-1))',
          '2': 'hsl(var(--chart-2))',
          '3': 'hsl(var(--chart-3))',
          '4': 'hsl(var(--chart-4))',
          '5': 'hsl(var(--chart-5))',
        },
        sidebar: {
          DEFAULT: 'hsl(var(--sidebar-background))',
          foreground: 'hsl(var(--sidebar-foreground))',
          primary: 'hsl(var(--sidebar-primary))',
          'primary-foreground': 'hsl(var(--sidebar-primary-foreground))',
          accent: 'hsl(var(--sidebar-accent))',
          'accent-foreground': 'hsl(var(--sidebar-accent-foreground))',
          border: 'hsl(var(--sidebar-border))',
          ring: 'hsl(var(--sidebar-ring))',
        },
      },
      borderRadius: {
        lg: 'var(--radius)',
        md: 'calc(var(--radius) - 2px)',
        sm: 'calc(var(--radius) - 4px)',
      },
    },
  },
  plugins: [
    require('tailwindcss-animate'), // Animation support
    require('@tailwindcss/forms'), // Forms plugin (if necessary)
    require('@tailwindcss/line-clamp'),
  ],
};
